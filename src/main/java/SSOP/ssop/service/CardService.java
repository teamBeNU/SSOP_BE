package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Avatar;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.card.request.*;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
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
    public boolean saveCard(CardCreateRequest request, Long user_id) {
        try {
            Card card;
            switch (request.getCard_template()) {
                case "student":
                    card = saveStudentCard(request.getStudent(), request, user_id);
                    break;
                case "worker":
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
                request.getCard_template(),
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
        card.setUserId(user_id); // 사용자 ID 설정
        cardStudentRepository.save(card); // 카드 저장
        return card;
    }

    private Card saveWorkerCard(CardWorkerCreateRequest workerRequest, CardCreateRequest request, Long user_id) {   //, String profileImageUrl
        CardWorker card = new CardWorker(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getCard_template(),
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

        List<CardResponse> cardResponses = cards.stream()
                .map(CardResponse::new)
                .collect(Collectors.toList());

        return cardResponses;

    }

    // 특정 카드 상세 조회
    public CardResponse getCard(long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return new CardResponse(card);
    }

    // 카드 수정
    public void updateCard(CardUpdateRequest request) {
        Card card = cardRepository.findById(request.getCard_id())
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        // 공통 필드 업데이트
        if (request.getCard_name() != null) {
            card.setCard_name(request.getCard_name());
        }
        if (request.getCard_introduction() != null) {
            card.setCard_introduction(request.getCard_introduction());
        }
        if (request.getCard_template() != null) {
            card.setCard_template(request.getCard_template());
        }
        if (request.getCard_cover() != null) {
            card.setCard_cover(request.getCard_cover());
        }
        if (request.getCard_SNS() != null) {
            card.setCard_SNS(request.getCard_SNS());
        }
        if (request.getCard_email() != null) {
            card.setCard_email(request.getCard_email());
        }
        if (request.getCard_MBTI() != null) {
            card.setCard_MBTI(request.getCard_MBTI());
        }
        if (request.getCard_music() != null) {
            card.setCard_music(request.getCard_music());
        }
        if (request.getCard_movie() != null) {
            card.setCard_movie(request.getCard_movie());
        }

        //템플릿 별 업데이트
        switch (card.getCard_template()) {
            case "student":
                if (request.getStudent() == null) {
                    break;
                }
                updateStudentCard((CardStudent) card, request.getStudent());
                break;
            case "worker":
                if (request.getWorker() == null) {
                    break;
                }
                updateWorkerCard((CardWorker) card, request.getWorker());
                break;
            default:
                throw new IllegalArgumentException("지정된 템플릿이 없습니다.");
        }
        cardRepository.save(card);
        throw new IllegalArgumentException("카드가 수정되었습니다.");
    }

    private void updateStudentCard(CardStudent card, CardStudentUpdateRequest studentRequest) {
        if (studentRequest.getCard_tel() != null) {
            card.setCard_tel(studentRequest.getCard_tel());
        }
        if (studentRequest.getCard_birth() != null) {
            card.setCard_birth(studentRequest.getCard_birth());
        }
        if (studentRequest.getCard_school() != null) {
            card.setCard_school(studentRequest.getCard_school());
        }
        if (studentRequest.getCard_grade() != null) {
            card.setCard_grade(studentRequest.getCard_grade());
        }
        if (studentRequest.getCard_student_major() != null) {
            card.setCard_student_major(studentRequest.getCard_student_major());
        }
        if (studentRequest.getCard_student_club() != null) {
            card.setCard_student_club(studentRequest.getCard_student_club());
        }
        if (studentRequest.getCard_student_role() != null) {
            card.setCard_student_role(studentRequest.getCard_student_role());
        }
    }

    private void updateWorkerCard(CardWorker card, CardWorkerUpdateRequest workerRequest) {
        if (workerRequest.getCard_tel() != null) {
            card.setCard_tel(workerRequest.getCard_tel());
        }
        if (workerRequest.getCard_birth() != null) {
            card.setCard_birth(workerRequest.getCard_birth());
        }
        if (workerRequest.getCard_job() != null) {
            card.setCard_job(workerRequest.getCard_job());
        }
    }


    // 카드 삭제
    public void deleteCard(long cardId, long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        cardRepository.delete(card);
    }

    // 상대 카드 메모 작성
    public void writeMemo(long card_id, long userId, String memo) {
        Card card = cardRepository.findById(card_id)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));

        if(card.getUserId() == userId) {
            throw new IllegalArgumentException("본인 카드입니다.");
        }

        if(memo == null) {
            throw new IllegalArgumentException("메모는 null일 수 없습니다.");
        } else {
            if(card.getMemo() == "") {
                card.setMemo(memo);
                cardRepository.save(card);
                throw new IllegalArgumentException("메모가 작성되었습니다.");
            } else {
                card.setMemo(memo);
                cardRepository.save(card);
                throw new IllegalArgumentException("메모가 수정되었습니다.");
            }
        }
    }
}