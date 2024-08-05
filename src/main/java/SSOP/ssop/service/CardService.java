package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.dto.card.response.ShowAllCardResponse;
import SSOP.ssop.repository.CardRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // 카드 생성
    public void createCard(long userId, Card card) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + userId));

        card.setUser(user);
        cardRepository.save(card);
    }

    // 전체 카드 조회
    public List<ShowAllCardResponse> getAllCards() {
        return cardRepository.findAll().stream()
                .map(ShowAllCardResponse::new)
                .collect(Collectors.toList());
    }

    // 내 카드 조회
    public List<ShowAllCardResponse> getMyCards(long userId) {
        return cardRepository.findByUser_UserId(userId).stream()
                .map(ShowAllCardResponse::new)
                .collect(Collectors.toList());
    }

    // 특정 카드 조회
    public CardResponse getCard(long card_id) {
        Card card = cardRepository.findById(card_id)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return new CardResponse(card);
    }

    // 카드 수정
    public void updateCard(CardUpdateRequest request) {
        Card card = cardRepository.findById(request.getCard_id())
                .orElseThrow(IllegalArgumentException::new);

        card.updateCard(request.getCard_template(), request.getCard_background(), request.getCard_name(), request.getCard_introduction(), request.getCard_SNS(), request.getCard_email(), request.getCard_MBTI(), request.getCard_music(), request.getCard_tel(), request.getCard_birth(), request.getCard_school(), request.getCard_grade(), request.getCard_studentId(), request.getCard_student_major(), request.getCard_student_role(), request.getCard_student_club());
        cardRepository.save(card);
    }

    // 카드 삭제
    public void deleteCard(long card_id) {
        Card card = cardRepository.findById(card_id)
                .orElseThrow(IllegalArgumentException::new);
        cardRepository.delete(card);
    }
}