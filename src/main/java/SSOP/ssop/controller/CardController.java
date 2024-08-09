package SSOP.ssop.controller;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.request.MemoRequest;
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

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/create")
    public void createCard(@RequestParam("userId") long userId, @RequestBody Card card) {
        cardService.createCard(userId, card);
    }

    // 모든 카드 조회
    @GetMapping("/view/total")
    public List<ShowAllCardResponse> getAllCards() {
        return cardService.getAllCards();
    }

    // 내 카드 목록 조회
    @GetMapping("/view/mine")
    public List<ShowAllCardResponse> getMyCards(@RequestParam("userId") long userId) {
        return cardService.getMyCards(userId);
    }

    // 상대 카드 목록 조회
    @GetMapping("/view/saved")
    public List<ShowAllCardResponse> getSavedCards(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId) {
        return cardService.getSavedCards(card_id, userId);
    }

    // 특정 카드 상세 조회
    @GetMapping("/view")
    public ResponseEntity<CardResponse> getCardsById(@RequestParam("card_id") long card_id) {
        CardResponse cardResponse = cardService.getCard(card_id);
        if (cardResponse != null) {
            return ResponseEntity.ok(cardResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 카드 수정 (내카드)
    @PatchMapping("/edit")
    public void updateCard(@RequestParam("card_id") long card_id, @RequestBody CardUpdateRequest request) {
        request.setCard_id(card_id);
        cardService.updateCard(request);
    }


    // 카드 삭제 (내카드 & 상대카드)
    @DeleteMapping("/delete")
    public void deleteCard(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId) {
        cardService.deleteCard(card_id, userId);
    }

    // 상대 카드 메모 작성
    @PostMapping("/memo")
    public void writeMemo(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId, @RequestBody MemoRequest memo) {
        cardService.writeMemo(card_id, userId, memo.getMemo());
    }

    // 상대 카드 메모 수정
    @PatchMapping("/memo")
    public void updateMemo(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId, @RequestBody MemoRequest memo) {
        cardService.updateMemo(card_id, userId, memo.getMemo());
    }
}
