package SSOP.ssop.service;

import SSOP.ssop.domain.card.Avatar;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.dto.request.CardStudentCreateRequest;
import SSOP.ssop.dto.request.CardWorkerCreateRequest;
import SSOP.ssop.repository.AvatarRepository;
import SSOP.ssop.repository.CardRepository;
import SSOP.ssop.repository.CardStudentRepository;
import SSOP.ssop.repository.CardWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private AvatarRepository avatarRepository;
    private CardStudentRepository cardStudentRepository;
    private CardWorkerRepository cardWorkerRepository;

    // 이미지 저장 경로 설정 (로컬 경로)
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/profiles/";

    @Autowired
    public CardService(CardRepository cardRepository, AvatarRepository avatarRepository, CardStudentRepository cardStudentRepository, CardWorkerRepository cardWorkerRepository) {
        this.cardRepository = cardRepository;
        this.avatarRepository = avatarRepository;
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
    }

    @Transactional
    public boolean saveCard(CardCreateRequest request, Long user_id) {  // , MultipartFile file
        try {
//            String profileImageUrl = saveImage(file);

            Card card;
            switch (request.getTemplate()) {
                case "student":
//                    saveStudentCard(request.getStudent(), request, user_id, profileImageUrl);
                    card = saveStudentCard(request.getStudent(), request, user_id);
                    break;
                case "worker":
//                    saveWorkerCard(request.getWorker(), request, user_id, profileImageUrl);
                    card = saveWorkerCard(request.getWorker(), request, user_id);
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

    private String saveImage(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + file.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        String filePath = UPLOAD_DIR + fileName;    // 저장할 파일 경로 설정

        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }

        // 파일 저장
        File saveFile = new File(filePath, fileName);
        file.transferTo(saveFile);

        return filePath;    //  저장된 파일 경로 리턴
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

    private Card saveStudentCard(CardStudentCreateRequest studentRequest, CardCreateRequest request, Long user_id) {    // , String profileImageUrl
        CardStudent card = new CardStudent(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getTemplate(),
                request.getCard_cover(),
                null, // Avatar를 나중에 설정

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
//  profileImageUrl,    // 저장된 이미지 URL
        card.setUser_id(user_id); // 사용자 ID 설정
        cardStudentRepository.save(card); // 카드 저장
//        avatar.setCard(card);   // Avatar와 Card를 연결
//        avatarRepository.save(avatar);  // Avatar 저장
        return card;
    }

    private Card saveWorkerCard(CardWorkerCreateRequest workerRequest, CardCreateRequest request, Long user_id) {   //, String profileImageUrl
        CardWorker card = new CardWorker(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getTemplate(),
                request.getCard_cover(),
                null, // Avatar를 나중에 설정

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
//profileImageUrl,    // 저장된 이미지 URL
        card.setUser_id(user_id); // 사용자 ID 설정
        cardWorkerRepository.save(card); // 카드 저장
//        avatar.setCard(card);   // Avatar와 Card를 연결
//        avatarRepository.save(avatar);  // Avatar 저장
        return card;
    }
}
