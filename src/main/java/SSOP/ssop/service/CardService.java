package SSOP.ssop.service;

import SSOP.ssop.domain.card.*;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.request.CardStudentCreateRequest;
import SSOP.ssop.dto.card.request.CardWorkerCreateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    private AvatarRepository avatarRepository;
    private CardStudentRepository cardStudentRepository;
    private CardWorkerRepository cardWorkerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CardService(CardRepository cardRepository, AvatarRepository avatarRepository, CardStudentRepository cardStudentRepository, CardWorkerRepository cardWorkerRepository) {
        this.cardRepository = cardRepository;
        this.avatarRepository = avatarRepository;
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
    }

    // 카드 생성
    @Transactional
    public boolean saveCard(CardCreateRequest request, Long user_id, MultipartFile file) {
        try {
            String profileImageUrl = null;
            if (file != null && !file.isEmpty()) {
                profileImageUrl = saveImage(file);
            }

            Card card;
            switch (request.getCard_template()) {
                case "student":
                    card = saveStudentCard(request.getStudent(), request, user_id, profileImageUrl);
                    break;
                case "worker":
                    card = saveWorkerCard(request.getWorker(), request, user_id, profileImageUrl);
                    break;
                default:
                    throw new IllegalArgumentException("템플릿 없음");
            }

            // Avatar 설정 및 저장
            if ("avatar".equals(request.getCard_cover())) {
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

    private String saveImage(MultipartFile file) throws Exception {

        String projectRootPath = new File("").getAbsolutePath();    // 프로젝트 폴더의 절대 경로
        String relativePath = "/src/main/resources/static/uploads/profiles/";    // 이미지 저장 경로 설정 (로컬 경로)
        String uploadDir = projectRootPath + relativePath;

//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("Cannot upload empty file");
//        }
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }

        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + file.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        String filePath = uploadDir + fileName;    // 저장할 파일 경로 설정
        System.out.println(filePath);
        System.out.println(fileName);
//        File directory = new File(uploadDir);
//        if (!directory.exists()) {
//            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
//        }

        // 파일 저장
//        File saveFile = new File(filePath);
        File saveFile = new File(directory, fileName);
        file.transferTo(saveFile);  // 파일 저장

        //return filePath;    // 저장된 파일 경로 리턴
        return "/uploads/profiles/" + fileName;
    }

    private Card saveStudentCard(CardStudentCreateRequest studentRequest, CardCreateRequest request, Long user_id, String profileImageUrl) {    // ,
        CardStudent card = new CardStudent(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getCard_template(),
                request.getCard_cover(),
                null, // Avatar를 나중에 설정
                profileImageUrl,    // 저장된 이미지 URL
                request.getCard_SNS(),
                request.getCard_email(),
                request.getCard_MBTI(),
                request.getCard_music(),
                request.getCard_movie(),
                studentRequest.getCard_tel(),
                studentRequest.getCard_birth(),
                studentRequest.getCard_bSecrete(),
                studentRequest.getCard_school(),
                studentRequest.getCard_grade(),
                studentRequest.getCard_student_major(),
                studentRequest.getCard_student_club(),
                studentRequest.getCard_student_role()
        );
        card.setUserId(user_id); // 사용자 ID 설정
        cardStudentRepository.save(card); // 카드 저장
        return card;
    }

    private Card saveWorkerCard(CardWorkerCreateRequest workerRequest, CardCreateRequest request, Long user_id, String profileImageUrl) {   //, String profileImageUrl
        CardWorker card = new CardWorker(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getCard_template(),
                request.getCard_cover(),
                null, // Avatar를 나중에 설정
                profileImageUrl,    // 저장된 이미지 URL
                request.getCard_SNS(),
                request.getCard_email(),
                request.getCard_MBTI(),
                request.getCard_music(),
                request.getCard_movie(),
                workerRequest.getCard_tel(),
                workerRequest.getCard_birth(),
                workerRequest.getCard_bSecrete(),
                workerRequest.getCard_job()
        );
        card.setUserId(user_id); // 사용자 ID 설정
        cardWorkerRepository.save(card); // 카드 저장
        return card;
    }

    // 모든 카드 조회
    public List<CardResponse> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardResponse::new)
                .collect(Collectors.toList());
    }

    // 내 카드 목록 조회
    public List<CardResponse> getMyCards(long userId) {
        return cardRepository.findByUserId(userId).stream()
                .map(CardResponse::new)
                .collect(Collectors.toList());
    }

    // 상대 카드 목록 조회
//    public List<ShowAllCardResponse> getSavedCards(long card_id, long userId) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("없는 카드 입니다. card_id : " + card_id));
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + userId));
//
//        if(card.getUser().getUserId() == userId) {
//            throw new IllegalArgumentException("본인 카드 입니다.");
//        }
//
//        if(!user.getSaved_card_list().contains(card_id)) {
//            throw new IllegalArgumentException("해당 카드는 보유 중이 아닙니다.");
//        }
//
//        return cardRepository.findById(card_id).stream()
//                .map(ShowAllCardResponse::new)
//                .collect(Collectors.toList());
//    }

    // 특정 카드 상세 조회
//    public CardResponse getCard(long card_id) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//        return new CardResponse(card);
//    }

    // 카드 수정
//    public void updateCard(CardUpdateRequest request) {
//        Card card = cardRepository.findById(request.getCard_id())
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        card.updateCard(request.getCard_template(), request.getcard_cover(), request.getCard_name(), request.getCard_introduction(), request.getCard_SNS(), request.getCard_email(), request.getCard_MBTI(), request.getCard_music(), request.getCard_tel(), request.getCard_birth(), request.getCard_school(), request.getCard_grade(), request.getCard_studentId(), request.getCard_student_major(), request.getCard_student_role(), request.getCard_student_club());
//        cardRepository.save(card);
//    }

    // 카드 삭제
//    public void deleteCard(long card_id, long userId) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        if(card.getUser().getUserId() == userId) {
//            cardRepository.delete(card);
//        }
//        else if (card.getUser().getUserId() != userId){
//            User user = userRepository.findById(userId)
//                    .orElseThrow(IllegalArgumentException::new);
//
//            user.deleteSavedList(card_id);
//
//            userRepository.save(user);
//        }
//    }

    // 상대 카드 메모 작성
//    public void writeMemo(long card_id, long userId, String memo) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        if(card.getUser().getUserId() == userId) {
//            throw new IllegalArgumentException("본인 카드입니다.");
//        }
//
//        card.setMemo(memo);
//        cardRepository.save(card);
//    }

    // 상대 카드 메모 수정
//    public void updateMemo(long card_id, long userId, String memo) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        if (memo == null) {
//            throw new IllegalArgumentException("메모는 null일 수 없습니다.");
//        }
//
//        card.setMemo(memo);
//        cardRepository.save(card);
//    }
}