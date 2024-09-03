package SSOP.ssop.controller;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.request.MemoRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.dto.card.response.CardSaveResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.CardService;
import SSOP.ssop.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final UserService userService;
    private final CardRepository cardRepository;
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService, UserService userService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.userService = userService;
        this.cardRepository = cardRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveCard(
            @Login Long userId,
            @RequestPart("card") CardCreateRequest request,
            @RequestPart(name = "image", required = false) MultipartFile file
    ) {
        try {
            // 카드 생성 서비스 호출
            boolean isSaved = cardService.saveCard(request, userId, file);

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
//    public ResponseEntity<List<CardResponse>> getSavedCards(@Login Long userId) {
//        List<CardResponse> cards = cardService.getSavedCards(userId);
//        return ResponseEntity.ok(cards);
//    }

    // 상대 카드 저장
    @PostMapping("/save")
    public ResponseEntity<CardSaveResponse> addCardToSavedList(@Login Long userId, @RequestParam Long cardId) {
        CardSaveResponse response = userService.addCardToSavedList(userId, cardId);
        return ResponseEntity.ok(response);
    }

    // 특정 카드 상세 조회
    @GetMapping("/view")
    public ResponseEntity<CardResponse> getCardsById(@RequestParam Long cardId) {
        CardResponse cardResponse = cardService.getCard(cardId);
        if (cardResponse != null) {
            return ResponseEntity.ok(cardResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 카드 수정 (내카드)
    @PatchMapping("/edit")
    public void updateCard(@Login Long userID, @RequestParam long cardId, @RequestBody CardUpdateRequest request) {
        request.setCard_id(cardId);
        cardService.updateCard(request);
        throw new CustomException(HttpStatus.OK, "카드가 수정되었습니다.");
    }

    // 카드 삭제 (내카드 & 상대카드)
    @DeleteMapping("/delete")
    public void deleteCard(@RequestParam long cardId, @Login Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다."));

        if (card.getUserId().equals(userId)) {
            cardService.deleteCard(cardId, userId);
            throw new CustomException(HttpStatus.OK, "내 카드를 삭제하였습니다.");
        } else {
            userService.deleteSavedCard(userId, cardId);
            throw new CustomException(HttpStatus.OK, "저장한 카드를 삭제했습니다.");
        }
    }

    // 상대 카드 메모
    @PostMapping("/memo")
    public void writeMemo(@RequestParam long cardId, @Login Long userId, @RequestBody MemoRequest memo) {
        cardService.writeMemo(cardId, userId, memo.getMemo());
    }
}