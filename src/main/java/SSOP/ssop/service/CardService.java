package SSOP.ssop.service;

import SSOP.ssop.controller.CustomException;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.*;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.*;
import SSOP.ssop.utils.CardUtils;
import SSOP.ssop.repository.Card.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    private AvatarRepository avatarRepository;
    private CardStudentRepository cardStudentRepository;
    private CardWorkerRepository cardWorkerRepository;
    private CardFanRepository cardFanRepository;
    private final CardUtils cardUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String PROFILE_IMG_DIR = "profile/";

    @Autowired
    public CardService(CardRepository cardRepository, AvatarRepository avatarRepository, CardStudentRepository cardStudentRepository, CardWorkerRepository cardWorkerRepository, CardFanRepository cardFanRepository, CardUtils cardUtils) {
        this.cardRepository = cardRepository;
        this.avatarRepository = avatarRepository;
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
        this.cardFanRepository = cardFanRepository;
        this.cardUtils = cardUtils;
    }

    // 카드 생성
    @Transactional
    public boolean saveCard(CardCreateRequest request, Long user_id, MultipartFile file) {
        try {
            String profileImageUrl = null;
            if (file != null && !file.isEmpty()) {
                profileImageUrl = uploadImage(file, user_id);
            }

            Card card = createCard(request, user_id, profileImageUrl);

            switch (request.getCardEssential().getCard_template()) {
                case "student":
                    saveStudentCard(card, request);
                    break;
                case "worker":
                    saveWorkerCard(card, request);
                    break;
                case "fan":
                    saveFanCard(card, request);
                    break;
                case "free":
                    saveStudentCard(card, request);
                    saveWorkerCard(card, request);
                    saveFanCard(card, request);
                    break;
                default:
                    throw new IllegalArgumentException("템플릿 없음");
            }

            // Avatar 설정 및 저장
            if ("avatar".equals(request.getCardEssential().getCard_cover()) && request.getAvatar() != null) {
                Avatar avatar = saveAvatar(request.getAvatar());
                avatar.setCard(card); // Avatar와 Card 연결
                avatarRepository.save(avatar);
                card.setAvatar(avatar); // Card에 Avatar 설정
                cardRepository.save(card); // 변경된 Card 저장
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Avatar saveAvatar(Avatar avatarRequest) {
        Avatar avatar = new Avatar();
        avatar.setFace(avatarRequest.getFace());
        avatar.setHair(avatarRequest.getHair());
        avatar.setHairColor(avatarRequest.getHairColor());
        avatar.setClothes(avatarRequest.getClothes());
        avatar.setAcc(avatarRequest.getAcc());
        avatar.setBg(avatarRequest.getBg());
        avatar.setBgColor(avatarRequest.getBgColor());
        return avatar;
    }

/*    private String saveImage(MultipartFile file) throws Exception {

        String projectRootPath = new File("").getAbsolutePath();    // 프로젝트 폴더의 절대 경로
        String relativePath = "/src/main/resources/static/uploads/profiles/";    // 이미지 저장 경로 설정 (로컬 경로)
        String uploadDir = projectRootPath + relativePath;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }

        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + file.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        // String filePath = uploadDir + fileName;    // 저장할 파일 경로 설정

        // 파일 저장
        File saveFile = new File(directory, fileName);
        file.transferTo(saveFile);  // 파일 저장

        //return filePath;    // 저장된 파일 경로 리턴
        return "/uploads/profiles/" + fileName;
    }*/

    // 이미지 업로드 (ASW S3 업로드)
    private String uploadImage(MultipartFile multipartFile, Long userId) throws IOException {
        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        String filePath = PROFILE_IMG_DIR + userId + "/" + fileName;   // 저장할 파일 경로

        File uploadFile = convertMultipartFileToFile(multipartFile)     // multipartFile을 file로 변환
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File 변환 실패"));

        // S3에 파일 업로드
        String fileUrl = uploadFileToS3(filePath, uploadFile);

        // 임시로 생성한 로컬 파일 삭제
        removeFile(uploadFile);

        return fileUrl;
    }

    // S3로 파일 업로드
    private String uploadFileToS3(String filePath, File uploadFile) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filePath)   // S3 내 디렉토리 및 파일 이름 설정
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));     // S3 업로드

        // 파일이 업로드된 S3의 URL 반환
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(filePath)).toExternalForm();
    }

    // 임시로 생성한 로컬 파일 삭제
    private void removeFile(File file) {
        if (file.delete()) {
            log.info("임시 로컬 파일 삭제 성공: {}", file);
            return;
        }
        log.info("임시 로컬 파일 삭제 실패: {}", file);
    }

    // MultipartFile을 File로 변환
    private Optional<File> convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());   // 운영체제의 임시 디렉토리 경로 가져옴(C:\Users\{사용자명}\AppData\Local\Temp\)
        try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
            fos.write(file.getBytes());
        }
        return Optional.of(convertFile);
    }

    private Card createCard(CardCreateRequest request, Long user_id, String profileImageUrl) {
        Card card = new Card(
                request.getCardEssential().getCard_name(),
                request.getCardEssential().getCard_introduction(),
                request.getCardEssential().getCard_template(),
                request.getCardEssential().getCard_cover(),
                null, // Avatar를 나중에 설정
                profileImageUrl,    // 저장된 이미지 URL
                request.getCardOptional().getCard_birth(),
                request.getCardOptional().getCard_bSecrete(),
                request.getCardOptional().getCard_tel(),
                request.getCardOptional().getCard_sns_insta(),
                request.getCardOptional().getCard_sns_x(),
                request.getCardOptional().getCard_email(),
                request.getCardOptional().getCard_MBTI(),
                request.getCardOptional().getCard_music(),
                request.getCardOptional().getCard_movie(),
                request.getCardOptional().getCard_hobby(),
                request.getCardOptional().getCard_address()
        );
        card.setUserId(user_id);
        cardRepository.save(card);
        return card;
    }

    private void saveStudentCard(Card card, CardCreateRequest request) {
        CardStudent cardStudent = new CardStudent(
                request.getStudent().getCard_student_school(),
                request.getStudent().getCard_student_grade(),
                request.getStudent().getCard_student_major(),
                request.getStudent().getCard_student_id(),
                request.getStudent().getCard_student_club(),
                request.getStudent().getCard_student_role(),
                request.getStudent().getCard_student_status()
        );
        cardStudent.setCard(card);  // Card 객체를 CardStudent에 설정
        cardStudentRepository.save(cardStudent); // 카드 저장
        //return card;
    }

    private void saveWorkerCard(Card card, CardCreateRequest request) {
        CardWorker cardWorker = new CardWorker(
                request.getWorker().getCard_worker_company(),
                request.getWorker().getCard_worker_job(),
                request.getWorker().getCard_worker_position(),
                request.getWorker().getCard_worker_department()
        );
        cardWorker.setCard(card);  // Card 객체를 CardStudent에 설정
        cardWorkerRepository.save(cardWorker); // 카드 저장
        //return card;
    }

    private void saveFanCard(Card card, CardCreateRequest request) {
        CardFan cardFan = new CardFan(
                request.getFan().getCard_fan_genre(),
                request.getFan().getCard_fan_first(),
                request.getFan().getCard_fan_second(),
                request.getFan().getCard_fan_reason()
        );
        cardFan.setCard(card);  // Card 객체를 CardStudent에 설정
        cardFanRepository.save(cardFan); // 카드 저장
        //return card;
    }

    // 모든 카드 조회
    public List<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            responses.add(cardUtils.createCardResponse(card, false));
        }

        return responses;
    }

    // 내 카드 목록 조회
    public List<CardResponse> getMyCards(long userId) {
        List<Card> cards = cardRepository.findByUserId(userId);
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
        responses.add(cardUtils.createCardResponse(card, false));
        }

        return responses;
    }

    // 상대 카드 목록 조회
//    public List<CardResponse> getSavedCards(long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저아이디입니다 : " + userId));
//
//        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();
//
//        if (savedCardList == null || savedCardList.isEmpty()) {
//            return Collections.emptyList(); // 저장한 카드가 없는 경우
//        }

//        List<Long> savedCardListAsLongs = savedCardList.stream()
//                .map(Long::valueOf)
//                .collect(Collectors.toList());

//        List<Card> cards = cardRepository.findAllById(savedCardListAsLongs);

//        List<CardResponse> responses = new ArrayList<>();

//        for (Card card : cards) {
//            responses.add(cardUtils.createCardResponse(card, true));
//        }

//        return responses;

//    }

    // 특정 카드 상세 조회
    public CardResponse getCard(long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return cardUtils.createCardResponse(card, false);
    }

    // 카드 수정
    public void updateCard(CardUpdateRequest request) {
        Card card = cardRepository.findById(request.getCard_id())
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        // 공통 필드 업데이트
        cardUtils.updateFieldIfNotNull(request.getCard_name(), card::setCard_name);
        cardUtils.updateFieldIfNotNull(request.getCard_introduction(), card::setCard_introduction);
        cardUtils.updateFieldIfNotNull(request.getCard_template(), card::setCard_template);
        cardUtils.updateFieldIfNotNull(request.getCard_cover(), card::setCard_cover);
        cardUtils.updateFieldIfNotNull(request.getAvatar(), card::setAvatar);
        cardUtils.updateFieldIfNotNull(request.getProfile_image_url(), card::setProfile_image_url);
        cardUtils.updateFieldIfNotNull(request.getCard_birth(), card::setCard_birth);
        cardUtils.updateFieldIfNotNull(request.getCard_bSecrete(), card::setCard_bSecrete);
        cardUtils.updateFieldIfNotNull(request.getCard_tel(), card::setCard_tel);
        cardUtils.updateFieldIfNotNull(request.getCard_sns_insta(), card::setCard_sns_insta);
        cardUtils.updateFieldIfNotNull(request.getCard_sns_x(), card::setCard_sns_x);
        cardUtils.updateFieldIfNotNull(request.getCard_email(), card::setCard_email);
        cardUtils.updateFieldIfNotNull(request.getCard_MBTI(), card::setCard_MBTI);
        cardUtils.updateFieldIfNotNull(request.getCard_music(), card::setCard_music);
        cardUtils.updateFieldIfNotNull(request.getCard_movie(), card::setCard_movie);
        cardUtils.updateFieldIfNotNull(request.getCard_hobby(), card::setCard_hobby);
        cardUtils.updateFieldIfNotNull(request.getCard_address(), card::setCard_address);

        // 템플릿 별 업데이트
        cardUtils.updateTemplateSpecificFields(card, request);
        card.setCreatedAt(LocalDateTime.now());
        cardRepository.save(card);
    }

    // 카드 삭제
    @Transactional
    public void deleteCard(long cardId, long userId) throws URISyntaxException {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        // AWS S3 파일 삭제
        String imageUrl = card.getProfile_image_url();      // card의 profile_image_url 가져오기
        deleteImage(imageUrl);

        // 카드 삭제
        String template = card.getCard_template();

        cardUtils.deleteIfExists(template, "student", cardStudentRepository, cardId);
        cardUtils.deleteIfExists(template, "worker", cardWorkerRepository, cardId);
        cardUtils.deleteIfExists(template, "fan", cardFanRepository, cardId);

        cardRepository.delete(card);
    }

    // AWS S3 파일 삭제
    private void deleteImage(String imageUrl) throws URISyntaxException {
        try {
            // Url에서 S3 키 추출
            URI uri = new URI(imageUrl);
            String fileKey = uri.getPath().substring(1);  // 경로의 첫 번째 '/' 제거

            // S3에서 객체 삭제
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileKey)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.error("이미지 삭제 성공");
        } catch (S3Exception e) {
            log.error("이미지 삭제 실패: {}", e.getMessage());
        }
    }

    // 상대 카드 메모 작성
    public void writeMemo(long cardId, long userId, String memo) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다"));

        if(card.getUserId() == userId) {
            throw new IllegalArgumentException("본인 카드입니다");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다"));

        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        if (savedCardList.containsKey(cardId)) {
            if(card.getMemo() == null) {
                card.setMemo(memo);
                cardRepository.save(card);
                throw new CustomException(HttpStatus.OK, "메모가 작성되었습니다");
            } else {
                card.setMemo(memo);
                cardRepository.save(card);
                throw new CustomException(HttpStatus.OK, "메모가 수정되었습니다");
            }
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "저장한 카드가 아닙니다.");
        }
    }
}