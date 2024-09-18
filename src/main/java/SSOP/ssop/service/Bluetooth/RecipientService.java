package SSOP.ssop.service.Bluetooth;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.Bluetooth.RecipientResponse;
import SSOP.ssop.repository.Card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipientService {
    private final CardRepository cardRepository;

    @Autowired
    public RecipientService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    // 수신자 목록 필터링 조회
    public List<RecipientResponse> getRecipients(String status, Long userId) {
        List<Card> cards;

        if (status == null || status.isEmpty()) {
            cards = cardRepository.findAllByRecipient_UserId(userId);
        } else {
            cards = cardRepository.findAllByRecipient_UserIdAndStatus(userId, status);
        }

        // Card 데이터를 RecipientResponse DTO로 변환
        return cards.stream()
                .map(card -> new RecipientResponse(card.getRecipient().getUserId(), card.getCard_name(), card.getStatus()))
                .collect(Collectors.toList());
    }
}
