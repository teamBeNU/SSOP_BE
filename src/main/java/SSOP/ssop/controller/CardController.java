package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 생성
    @PostMapping("/create")
    public ResponseEntity<?> saveCard(@RequestBody CardCreateRequest request) {
        try {
            // 현재 인증된 사용자의 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 인증 정보가 없을 경우 처리
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "사용자 인증 필요"));
            }

            UserDetail userDetail = (UserDetail) authentication.getPrincipal();

            // 인증된 사용자의 userId 가져오기
            Long authenticatedUserId = userDetail.getUser().getUserId();

            // 카드 생성 서비스 호출
            boolean isSaved = cardService.saveCard(request, authenticatedUserId);

            // 카드 생성이 성공적으로 이루어졌는지 확인
            if (isSaved) {
                return ResponseEntity.ok(Map.of("code", 200, "message", "카드 생성 완료"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("code", 400, "message", "카드 생성 실패"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "message", "서버 오류 발생"));
        }
    }

    // 모든 카드 조회
    @GetMapping("/view/total")
    public ResponseEntity<List<CardResponse>> getAllCards() {
        List<CardResponse> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    // 내 카드 목록 조회
    @GetMapping("/view/mine")
    public ResponseEntity<List<CardResponse>> getMyCards(@Login Long userId) {
        List<CardResponse> cards = cardService.getMyCards(userId);
        return ResponseEntity.ok(cards);
    }

    // 상대 카드 목록 조회
//    @GetMapping("/view/saved")
//    public List<ShowAllCardResponse> getSavedCards(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId) {
//        return cardService.getSavedCards(card_id, userId);
//    }

    // 특정 카드 상세 조회
//    @GetMapping("/view")
//    public ResponseEntity<CardResponse> getCardsById(@RequestParam("card_id") long card_id) {
//        CardResponse cardResponse = cardService.getCard(card_id);
//        if (cardResponse != null) {
//            return ResponseEntity.ok(cardResponse);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    // 카드 수정 (내카드)
//    @PatchMapping("/edit")
//    public void updateCard(@RequestParam("card_id") long card_id, @RequestBody CardUpdateRequest request) {
//        request.setCard_id(card_id);
//        cardService.updateCard(request);
//    }


    // 카드 삭제 (내카드 & 상대카드)
//    @DeleteMapping("/delete")
//    public void deleteCard(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId) {
//        cardService.deleteCard(card_id, userId);
//    }

    // 상대 카드 메모 작성
//    @PostMapping("/memo")
//    public void writeMemo(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId, @RequestBody MemoRequest memo) {
//        cardService.writeMemo(card_id, userId, memo.getMemo());
//    }

    // 상대 카드 메모 수정
//    @PatchMapping("/memo")
//    public void updateMemo(@RequestParam("card_id") long card_id, @RequestParam("userId") long userId, @RequestBody MemoRequest memo) {
//        cardService.updateMemo(card_id, userId, memo.getMemo());
//    }
}
