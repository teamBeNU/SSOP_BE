package SSOP.ssop.controller;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.dto.card.response.ShowAllCardResponse;
import SSOP.ssop.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/create/{userId}")
    public void createCard(@PathVariable("userId") long userId, @RequestBody Card card) {
        cardService.createCard(userId, card);
    }

    // 모든 카드 조회
    @GetMapping
    public List<ShowAllCardResponse> getAllCards() {
        return cardService.getAllCards();
    }

    // 내 카드 조회
    @GetMapping("/mine/{userId}")
    public List<CardResponse> getMyCards(@PathVariable("userId") long userId) {
        return cardService.getMyCards(userId);
    }

    // 특정 카드 조회
    @GetMapping("/{card_id}")
    public ResponseEntity<CardResponse> getCardsById(@PathVariable("card_id") long card_id) {
        CardResponse cardResponse = cardService.getCard(card_id);
        if (cardResponse != null) {
            return ResponseEntity.ok(cardResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 카드 수정
    @PatchMapping("/{card_id}")
    public void updateCard(@PathVariable("card_id") long card_id, @RequestBody CardUpdateRequest request) {
        request.setCard_id(card_id);
        cardService.updateCard(request);
    }


    // 카드 삭제
    @DeleteMapping("/{card_id}")
    public void deleteCard(@PathVariable("card_id") long card_id) {
        cardService.deleteCard(card_id);
    }

}
