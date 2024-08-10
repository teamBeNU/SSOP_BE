package SSOP.ssop.service;

import SSOP.ssop.domain.card.Card;
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


    @Autowired
    public CardService(CardRepository cardRepository, CardStudentRepository cardStudentRepository) {
        this.cardRepository = cardRepository;
        this.cardStudentRepository = cardStudentRepository;
    }

    @Transactional
    public void saveCard(CardCreateRequest request) {

        switch (request.getTemplate()) {
            case "STUDENT":
                saveStudentCard(request.getCardStudentCreateRequest(), request);
                break;
            default:
                throw new IllegalArgumentException("템플릿 없음");
        }
    }

    private void saveStudentCard(CardStudentCreateRequest studentRequest, CardCreateRequest request) {
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

        cardStudentRepository.save(card);
    }

}
