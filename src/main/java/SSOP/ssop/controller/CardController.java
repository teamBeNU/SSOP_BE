package SSOP.ssop.controller;

import SSOP.ssop.domain.Card;
import SSOP.ssop.dto.response.CardResponse;
import SSOP.ssop.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/mine")
    public ResponseEntity<List<CardResponse>> getCardsById(@RequestParam Long id) {
        List<CardResponse> cards = cardService.getCardsById(id);
        if (cards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cards);
    }

}
