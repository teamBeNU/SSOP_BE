package SSOP.ssop.service;

import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.dto.request.CardStudentCreateRequest;
import SSOP.ssop.dto.request.CardWorkerCreateRequest;
import SSOP.ssop.repository.CardRepository;
import SSOP.ssop.repository.CardStudentRepository;
import SSOP.ssop.repository.CardWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private CardStudentRepository cardStudentRepository;
    private CardWorkerRepository cardWorkerRepository;


    @Autowired
    public CardService(CardRepository cardRepository, CardStudentRepository cardStudentRepository, CardWorkerRepository cardWorkerRepository) {
        this.cardRepository = cardRepository;
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
    }

    @Transactional
    public void saveCard(CardCreateRequest request, Long user_id) {

        switch (request.getTemplate()) {
            case "STUDENT":
                saveStudentCard(request.getCardStudentCreateRequest(), request, user_id);
                break;
            case "WORKER":
                saveWorkerCard(request.getCardWorkerCreateRequest(), request, user_id);
                break;
            default:
                throw new IllegalArgumentException("템플릿 없음");
        }
    }

    private void saveStudentCard(CardStudentCreateRequest studentRequest, CardCreateRequest request, Long user_id) {
        CardStudent card = new CardStudent(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getTemplate(),
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

        card.setUser_id(user_id); // 사용자 ID 설정
        cardStudentRepository.save(card); // 카드 저장
    }

    private void saveWorkerCard(CardWorkerCreateRequest workerRequest, CardCreateRequest request, Long user_id) {
        CardWorker card = new CardWorker(
                request.getCard_name(),
                request.getCard_introduction(),
                request.getTemplate(),
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

        card.setUser_id(user_id); // 사용자 ID 설정
        cardWorkerRepository.save(card); // 카드 저장
    }
}
