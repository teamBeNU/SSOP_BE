package SSOP.ssop.service;

import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.repository.CardRepositoryV1;
import org.springframework.stereotype.Service;

@Service
public class CardServiceV1 {

    private final CardRepositoryV1 cardRepositoryV1;

    public CardServiceV1(CardRepositoryV1 cardRepositoryV1) {
        this.cardRepositoryV1 = cardRepositoryV1;
    }

    public void saveCard(CardCreateRequest request) {
        cardRepositoryV1.saveCard(request.getCard_name(), request.getCard_introduction());
    }
}
