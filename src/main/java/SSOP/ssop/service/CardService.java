package SSOP.ssop.service;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.dto.request.CardStudentCreateRequest;
import SSOP.ssop.repository.CardRepository;
import SSOP.ssop.repository.CardStudentRepository;
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

//    @Transactional
//    public void saveCard(CardCreateRequest request) {
//        Card card = cardRepository.save(new Card(request.getCard_name(), request.getCard_introduction()));
//    }

//    @Transactional
//    public void saveCard(Card card, String template) {
//
//        switch (template.toLowerCase()) {
//            case "student":
//                saveStudentCard((CardStudent) card);
//                break;
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
//
//    private void saveStudentCard(CardStudent card) {
//        cardRepository.save(card);
//        cardStudentRepository.save(card);
//    }

    @Transactional
    public void saveCard(CardCreateRequest request) {

        switch (request.getTemplate()) {
            case "STUDENT":
                saveStudentCard(request.getCardStudentCreateRequest(), request);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void saveStudentCard(CardStudentCreateRequest studentRequest, CardCreateRequest request) {
        CardStudent card = new CardStudent();
        populateCommonFields(card, request);

        card.setCard_tel(studentRequest.getCard_tel());
        card.setCard_birth(studentRequest.getCard_birth());
        card.setCard_bSecrete(studentRequest.getCard_bSecrete());
        card.setCard_school(studentRequest.getCard_school());
        card.setCard_grade(studentRequest.getCard_grade());
        card.setCard_student_major(studentRequest.getCard_student_major());
        card.setCard_student_club(studentRequest.getCard_student_club());
        card.setCard_student_role(studentRequest.getCard_student_role());

        cardStudentRepository.save(card);
    }

    private void populateCommonFields(Card card, CardCreateRequest request) {
        card.setCard_name(request.getCard_name());
        card.setCard_introduction(request.getCard_introduction());
        card.setTemplate(request.getTemplate());
        card.setCard_SNS(request.getCard_SNS());
        card.setCard_email(request.getCard_email());
        card.setCard_MBTI(request.getCard_MBTI());
        card.setCard_music(request.getCard_music());
        card.setCard_movie(request.getCard_movie());
    }
}
