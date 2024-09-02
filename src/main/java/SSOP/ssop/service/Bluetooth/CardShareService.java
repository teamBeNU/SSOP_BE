package SSOP.ssop.service.Bluetooth;

import SSOP.ssop.domain.bluetooth.CardShareRequest;
import SSOP.ssop.repository.Bluetooth.CardShareRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardShareService {

    private final CardShareRequestRepository cardShareRequestRepository;

    @Autowired
    public CardShareService(CardShareRequestRepository cardShareRequestRepository) {
        this.cardShareRequestRepository = cardShareRequestRepository;
    }

    public CardShareRequest createShareRequest(Long cardId, Long recipientId) {
        CardShareRequest request = new CardShareRequest();
        request.setCardId(cardId);
        request.setRecipientId(recipientId);
        request.setStatus("요청 중...");
        return cardShareRequestRepository.save(request);
    }

    public List<CardShareRequest> getShareRequestsByRecipientId(Long recipientId) {
        return cardShareRequestRepository.findByRecipientId(recipientId);
    }
}
