package SSOP.ssop.service;

import SSOP.ssop.domain.Card;
import SSOP.ssop.dto.response.CardResponse;
import SSOP.ssop.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    // 카드 생성
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    // 카드 조회
    public CardResponse getMyCards(long userId) {
        Card card = cardRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("카드가 존재하지 않습니다."));
        return new CardResponse(card);
    }
}