package SSOP.ssop.controller;

import SSOP.ssop.domain.Card;
import SSOP.ssop.dto.response.CardResponse;
import SSOP.ssop.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/create/{userId}")
    public void saveCard(@PathVariable("userId") long userId, @RequestBody Card card) {
        cardService.saveCard(userId, card);
    }

//    public void saveCard(@RequestBody Card card) {
//        cardService.saveCard(card);
//    }

    // 카드 조회
    @GetMapping("/mine")
    public ResponseEntity<CardResponse> getMyCards(@RequestParam("card_id") long card_id) {
        CardResponse cardResponse = cardService.getMyCards(card_id);
        if (cardResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(cardResponse);
        }
    }

}
