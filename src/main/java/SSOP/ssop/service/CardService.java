package SSOP.ssop.service;

import SSOP.ssop.controller.CustomException;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.*;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.dto.card.response.CardShareResponse;
import SSOP.ssop.dto.card.response.CardShareStatusResponse;
import SSOP.ssop.repository.Card.*;
import SSOP.ssop.repository.TeamSp.TeamSpMemberRepository;
import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.utils.CardUtils;
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
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private TeamSpMemberRepository teamSpMemberRepository;

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String CARD_IMG_DIR = "card/";

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

            String cardTemplate = request.getCardEssential().getCard_template();

            if ("student".equals(cardTemplate) || "studentSchool".equals(cardTemplate) ||
                    "studentUniv".equals(cardTemplate) || "worker".equals(cardTemplate) ||
                    "fan".equals(cardTemplate) || "free".equals(cardTemplate)) {

                Card card = createCard(request, user_id, profileImageUrl);

                switch (cardTemplate) {
                    case "student":
                    case "studentSchool":           // 초,중,고등학생
                    case "studentUniv":             // 대학(원)생
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
                        throw new IllegalArgumentException("템플릿이 존재하지 않습니다.");
                }

                // Avatar 설정 및 저장
                if ("avatar".equals(request.getCardEssential().getCard_cover()) && request.getAvatar() != null) {
                    Avatar avatar = saveAvatar(request.getAvatar());
                    avatar.setCard(card); // Avatar와 Card 연결
                    avatarRepository.save(avatar);
                    card.setAvatar(avatar); // Card에 Avatar 설정
                    cardRepository.save(card); // 변경된 Card 저장
                }
            } else {
                throw new IllegalArgumentException("존재하지 않은 카드 템플릿입니다: " + cardTemplate);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 생성 - 아바타
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

    // 이미지 업로드 (ASW S3 업로드)
    private String uploadImage(MultipartFile multipartFile, Long userId) throws IOException {
        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        String filePath = CARD_IMG_DIR + userId + "/" + fileName;   // 저장할 파일 경로

        // S3에 파일 업로드
        String fileUrl = uploadFileToS3(filePath, multipartFile.getInputStream());

        return fileUrl;
    }

    // S3로 파일 업로드
    private String uploadFileToS3(String filePath, InputStream inputStream) throws IOException {   // File uploadFile
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filePath)   // S3 내 디렉토리 및 파일 이름 설정
                .build();

        // S3 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));

        // 파일이 업로드된 S3의 URL 반환
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(filePath)).toExternalForm();
    }

    // 생성 - 카드
    private Card createCard(CardCreateRequest request, Long user_id, String profileImageUrl) {
        Card card = new Card(
                request.getCardEssential().getCard_name(),
                request.getCardEssential().getCard_introduction(),
                request.getCardEssential().getCard_template(),
                request.getCardEssential().getCard_cover(),
                null, // Avatar를 나중에 설정
                profileImageUrl,    // 저장된 이미지 URL
                request.getCardOptional().getCard_birth(),
                request.getCardOptional().getCard_bSecret(),
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

    // 생성 - 학생
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
    }

    // 생성 - 직장인
    private void saveWorkerCard(Card card, CardCreateRequest request) {
        CardWorker cardWorker = new CardWorker(
                request.getWorker().getCard_worker_company(),
                request.getWorker().getCard_worker_job(),
                request.getWorker().getCard_worker_position(),
                request.getWorker().getCard_worker_department()
        );
        cardWorker.setCard(card);  // Card 객체를 CardStudent에 설정
        cardWorkerRepository.save(cardWorker); // 카드 저장
    }

    // 생성 - 팬
    private void saveFanCard(Card card, CardCreateRequest request) {
        CardFan cardFan = new CardFan(
                request.getFan().getCard_fan_genre(),
                request.getFan().getCard_fan_first(),
                request.getFan().getCard_fan_second(),
                request.getFan().getCard_fan_reason()
        );
        cardFan.setCard(card);  // Card 객체를 CardStudent에 설정
        cardFanRepository.save(cardFan); // 카드 저장
    }

    // 모든 카드 조회
    public List<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            responses.add(cardUtils.createCardResponse(card, false, null));
        }

        return responses;
    }

    // 내 카드 목록 조회
    public List<CardResponse> getMyCards(long userId) {
        List<Card> cards = cardRepository.findByUserId(userId);
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            responses.add(cardUtils.createCardResponse(card, false, card.getCreatedAt()));
        }

        return responses;
    }

    // 상대 카드 목록 조회
    public List<CardResponse> getSavedCards(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저아이디입니다 : " + userId));

        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        if (savedCardList == null || savedCardList.isEmpty()) {
            return Collections.emptyList(); // 저장한 카드가 없는 경우
        }

        List<Long> savedCardListAsLongs = new ArrayList<>(savedCardList.keySet());

        List<Card> cards = cardRepository.findAllById(savedCardListAsLongs);

        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            LocalDateTime savedAt = savedCardList.get(card.getCardId());
            responses.add(cardUtils.createCardResponse(card, true, savedAt));
        }

        return responses;
    }

    // 특정 카드 상세 조회
    public CardResponse getCard(long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return cardUtils.createCardResponse(card, false, card.getCreatedAt());
    }

    // 카드 수정
    public void updateCard(CardUpdateRequest request, MultipartFile file) throws URISyntaxException, IOException {
        Card card = cardRepository.findById(request.getCard_id())
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        // 이미지가 존재할 때만 수정
        if (file != null && !file.isEmpty()) {
            // AWS S3 이미지 삭제
            String imageUrl = card.getProfile_image_url();      // card의 profile_image_url 가져오기
            deleteImage(imageUrl);

            // AWS S3 이미지 업로드
            String profileImageUrl = null;
            profileImageUrl = uploadImage(file, card.getUserId());

            // profile_image_url 필드 업데이트
            cardUtils.updateFieldIfNotNull(profileImageUrl, card::setProfile_image_url);
        }

        // 공통 필드 업데이트
        cardUtils.updateFieldIfNotNull(request.getCard_name(), card::setCard_name);
        cardUtils.updateFieldIfNotNull(request.getCard_introduction(), card::setCard_introduction);
        cardUtils.updateFieldIfNotNull(request.getCard_template(), card::setCard_template);
        cardUtils.updateFieldIfNotNull(request.getCard_cover(), card::setCard_cover);
        cardUtils.updateFieldIfNotNull(request.getAvatar(), card::setAvatar);
        cardUtils.updateFieldIfNotNull(request.getCard_birth(), card::setCard_birth);
        cardUtils.updateFieldIfNotNull(request.getCard_bSecret(), card::setCard_bSecret);
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

        boolean isCardInTeamSpace = teamSpMemberRepository.existsById(cardId);
        if (isCardInTeamSpace) {
            throw new IllegalArgumentException("팀스페이스에 제출한 카드는 삭제할 수 없습니다. 카드아이디: " + cardId);
        }

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

            log.info("이미지 삭제 성공");
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

    // 카드 공유 처리
    public CardShareResponse shareCard(Long userId, Long cardId, Long recipientId) throws IllegalArgumentException {
        // 카드 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        // 카드 소유자가 아닌 경우 예외 처리
        if (card.getUser().getUserId() != userId) {
            throw new IllegalArgumentException("본인의 카드만 공유할 수 있습니다.");
        }

        // 수신자 조회
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("수신자를 찾을 수 없습니다."));

        // 카드 수신자 설정 및 상태 업데이트
        card.setRecipient(recipient);
        card.setStatus("요청 중...");

        // 변경된 카드 저장
        cardRepository.save(card);

        return new CardShareResponse(cardId, recipientId, card.getStatus());
    }

    // 모든 공유된 카드의 상태 조회
    public List<CardShareStatusResponse> getAllCardShareStatus(Long userId) {
        // 로그인된 사용자가 공유한 모든 카드 조회
        List<Card> cards = cardRepository.findAllByUser_UserId(userId);

        // 카드 리스트를 공유 상태 응답 리스트로 변환
        return cards.stream()
                .map(card -> new CardShareStatusResponse(
                        card.getCardId(),
                        card.getRecipient().getUserId(),
                        card.getStatus()))
                .collect(Collectors.toList());
    }

    // 사용자의 저장된 카드 ID 목록을 가져오기
    public List<Long> getSavedCardIds(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 아이디입니다 : " + userId));
        return new ArrayList<>(user.getSaved_card_list().keySet());
    }
}