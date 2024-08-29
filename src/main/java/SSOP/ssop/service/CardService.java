package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.*;
import SSOP.ssop.dto.card.request.*;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    private AvatarRepository avatarRepository;
    private CardStudentRepository cardStudentRepository;
    private CardWorkerRepository cardWorkerRepository;
    private CardFanRepository cardFanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CardService(CardRepository cardRepository, AvatarRepository avatarRepository, CardStudentRepository cardStudentRepository, CardWorkerRepository cardWorkerRepository, CardFanRepository cardFanRepository) {
        this.cardRepository = cardRepository;
        this.avatarRepository = avatarRepository;
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
        this.cardFanRepository = cardFanRepository;
    }

    // 카드 생성
    @Transactional
    public boolean saveCard(CardCreateRequest request, Long user_id, MultipartFile file) {
        try {
            String profileImageUrl = null;
            if (file != null && !file.isEmpty()) {
                profileImageUrl = saveImage(file);
            }

            //Card card;
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

    private String saveImage(MultipartFile file) throws Exception {

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

    // 카드 조회 응답
    private CardResponse createCardResponse(Card card) {
        CardStudent cardStudent = null;
        CardWorker cardWorker = null;
        CardFan cardFan = null;

        switch (card.getCard_template()) {
            case "student":
                cardStudent = cardStudentRepository.findByCard_CardId(card.getCardId());
                break;
            case "worker":
                cardWorker = cardWorkerRepository.findByCard_CardId(card.getCardId());
                break;
            case "fan":
                cardFan = cardFanRepository.findByCard_CardId(card.getCardId());
                break;
            case "free":
                cardStudent = cardStudentRepository.findByCard_CardId(card.getCardId());
                cardWorker = cardWorkerRepository.findByCard_CardId(card.getCardId());
                cardFan = cardFanRepository.findByCard_CardId(card.getCardId());
                break;
        }

        return new CardResponse(card, cardStudent, cardWorker, cardFan);
    }


    // 모든 카드 조회
    public List<CardResponse> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            responses.add(createCardResponse(card));
        }

        return responses;
    }

    // 내 카드 목록 조회
    public List<CardResponse> getMyCards(long userId) {
        List<Card> cards = cardRepository.findByUserId(userId);
        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
        responses.add(createCardResponse(card));
        }

        return responses;
    }

    // 상대 카드 목록 조회
    public List<CardResponse> getSavedCards(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저아이디입니다 : " + userId));

        List<String> savedCardList = user.getSaved_card_list();

        if (savedCardList == null || savedCardList.isEmpty()) {
            return Collections.emptyList(); // 저장한 카드가 없는 경우
        }

        List<Long> savedCardListAsLongs = savedCardList.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        List<Card> cards = cardRepository.findAllById(savedCardListAsLongs);

        List<CardResponse> responses = new ArrayList<>();

        for (Card card : cards) {
            responses.add(createCardResponse(card));
        }

        return responses;

    }

    // 특정 카드 상세 조회
    public CardResponse getCard(long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return createCardResponse(card);
    }

    // 카드 수정
    public void updateCard(CardUpdateRequest request) {
        Card card = cardRepository.findById(request.getCard_id())
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        // 공통 필드 업데이트
        updateFieldIfNotNull(request.getCard_name(), card::setCard_name);
        updateFieldIfNotNull(request.getCard_introduction(), card::setCard_introduction);
        updateFieldIfNotNull(request.getCard_template(), card::setCard_template);
        updateFieldIfNotNull(request.getCard_cover(), card::setCard_cover);
        updateFieldIfNotNull(request.getAvatar(), card::setAvatar);
        updateFieldIfNotNull(request.getProfile_image_url(), card::setProfile_image_url);
        updateFieldIfNotNull(request.getCard_birth(), card::setCard_birth);
        updateFieldIfNotNull(request.getCard_bSecrete(), card::setCard_bSecrete);
        updateFieldIfNotNull(request.getCard_tel(), card::setCard_tel);
        updateFieldIfNotNull(request.getCard_sns_insta(), card::setCard_sns_insta);
        updateFieldIfNotNull(request.getCard_sns_x(), card::setCard_sns_x);
        updateFieldIfNotNull(request.getCard_email(), card::setCard_email);
        updateFieldIfNotNull(request.getCard_MBTI(), card::setCard_MBTI);
        updateFieldIfNotNull(request.getCard_music(), card::setCard_music);
        updateFieldIfNotNull(request.getCard_movie(), card::setCard_movie);
        updateFieldIfNotNull(request.getCard_hobby(), card::setCard_hobby);
        updateFieldIfNotNull(request.getCard_address(), card::setCard_address);

        // 템플릿 별 업데이트
        updateTemplateSpecificFields(card, request);

        cardRepository.save(card);
    }

    private <T> void updateFieldIfNotNull(T value, Consumer<T> updater) {
        if (value != null) {
            updater.accept(value);
        }
    }

    private void updateTemplateSpecificFields(Card card, CardUpdateRequest request) {
        Long cardId = card.getCardId();

        switch (card.getCard_template()) {
            case "student":
                if (request.getStudent() != null) {
                    CardStudent cardStudent = cardStudentRepository.findByCard_CardId(cardId);
                    if (cardStudent != null) {
                        updateStudentCard(cardStudent, request.getStudent());
                    } else {
                        throw new IllegalArgumentException("CardStudent entity not found for this card.");
                    }
                }
                break;
            case "worker":
                if (request.getWorker() != null) {
                    CardWorker cardWorker = cardWorkerRepository.findByCard_CardId(cardId);
                    if (cardWorker != null) {
                        updateWorkerCard(cardWorker, request.getWorker());
                    } else {
                        throw new IllegalArgumentException("CardWorker entity not found for this card.");
                    }
                }
                break;
            case "fan":
                if (request.getFan() != null) {
                    CardFan cardFan = cardFanRepository.findByCard_CardId(cardId);
                    if (cardFan != null) {
                        updateFanCard(cardFan, request.getFan());
                    } else {
                        throw new IllegalArgumentException("CardFan entity not found for this card");
                    }
                }
                break;
            case "free":
                if (request.getStudent() != null) {
                    CardStudent cardStudent = cardStudentRepository.findByCard_CardId(cardId);
                    if (cardStudent != null) {
                        updateStudentCard(cardStudent, request.getStudent());
                    } else {
                        throw new IllegalArgumentException("CardStudent entity not found for this card.");
                    }
                }
                if (request.getWorker() != null) {
                    CardWorker cardWorker = cardWorkerRepository.findByCard_CardId(cardId);
                    if (cardWorker != null) {
                        updateWorkerCard(cardWorker, request.getWorker());
                    } else {
                        throw new IllegalArgumentException("CardWorker entity not found for this card.");
                    }
                }
                if (request.getFan() != null) {
                    CardFan cardFan = cardFanRepository.findByCard_CardId(cardId);
                    if (cardFan != null) {
                        updateFanCard(cardFan, request.getFan());
                    } else {
                        throw new IllegalArgumentException("CardFan entity not found for this card");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("지정된 템플릿이 없습니다.");
        }
    }

    private void updateStudentCard(CardStudent card, CardStudentUpdateRequest studentRequest) {
        updateFieldIfNotNull(studentRequest.getCard_student_school(), card::setCard_student_school);
        updateFieldIfNotNull(studentRequest.getCard_student_grade(), card::setCard_student_grade);
        updateFieldIfNotNull(studentRequest.getCard_student_major(), card::setCard_student_major);
        updateFieldIfNotNull(studentRequest.getCard_student_id(), card::setCard_student_id);
        updateFieldIfNotNull(studentRequest.getCard_student_club(), card::setCard_student_club);
        updateFieldIfNotNull(studentRequest.getCard_student_role(), card::setCard_student_role);
        updateFieldIfNotNull(studentRequest.getCard_student_status(), card::setCard_student_status);
    }

    private void updateWorkerCard(CardWorker card, CardWorkerUpdateRequest workerRequest) {
        updateFieldIfNotNull(workerRequest.getCard_worker_company(), card::setCard_worker_company);
        updateFieldIfNotNull(workerRequest.getCard_worker_job(), card::setCard_worker_job);
        updateFieldIfNotNull(workerRequest.getCard_worker_position(), card::setCard_worker_position);
        updateFieldIfNotNull(workerRequest.getCard_worker_department(), card::setCard_worker_department);
    }

    private void updateFanCard(CardFan card, CardFanUpdateRequest fanRequest) {
        updateFieldIfNotNull(fanRequest.getCard_fan_genre(), card::setCard_fan_genre);
        updateFieldIfNotNull(fanRequest.getCard_fan_first(), card::setCard_fan_first);
        updateFieldIfNotNull(fanRequest.getCard_fan_second(), card::setCard_fan_second);
        updateFieldIfNotNull(fanRequest.getCard_fan_reason(), card::setCard_fan_reason);
    }


//    // 카드 삭제
//    public void deleteCard(long cardId, long userId) {
//        Card card = cardRepository.findById(cardId)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        cardRepository.delete(card);
//    }
//
//    // 상대 카드 메모 작성
//    public void writeMemo(long card_id, long userId, String memo) {
//        Card card = cardRepository.findById(card_id)
//                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
//
//        if(card.getUserId() == userId) {
//            throw new IllegalArgumentException("본인 카드입니다.");
//        }
//
//        if(memo == null) {
//            throw new IllegalArgumentException("메모는 null일 수 없습니다.");
//        } else {
//            if(card.getMemo() == "") {
//                card.setMemo(memo);
//                cardRepository.save(card);
//                throw new IllegalArgumentException("메모가 작성되었습니다.");
//            } else {
//                card.setMemo(memo);
//                cardRepository.save(card);
//                throw new IllegalArgumentException("메모가 수정되었습니다.");
//            }
//        }
//    }
}