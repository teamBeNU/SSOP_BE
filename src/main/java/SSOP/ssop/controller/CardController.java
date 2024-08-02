package SSOP.ssop.controller;

import SSOP.ssop.domain.Card;
import SSOP.ssop.dto.response.CardResponse;
import SSOP.ssop.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/create")
    public void saveCard(@RequestBody Card card) {
        cardService.saveCard(card);
    }

    // 카드 조회
    @GetMapping("/mine")
    public ResponseEntity<CardResponse> getMyCards(@PathVariable("userId") long userId) {
        CardResponse cardResponse = cardService.getMyCards(userId);
        if (cardResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(cardResponse);
        }
    }

}
