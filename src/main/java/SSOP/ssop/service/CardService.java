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

    @Transactional(readOnly = true)
    public List<CardResponse> getCardsById(Long id) {

        if ( cardRepository.findCardsById(id).isEmpty()) {
            throw new CardNotFoundException("No cards found for user with ID: " + id);
        }
        return  cardRepository.findCardsById(id).stream()
                .map(CardResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public class CardNotFoundException extends RuntimeException {
        public CardNotFoundException(String message) {
            super(message);
        }
    }
}