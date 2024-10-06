package SSOP.ssop.controller;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.dto.card.request.CardShareRequest;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.request.MemoRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.dto.card.response.CardSaveResponse;
import SSOP.ssop.dto.card.response.CardShareResponse;
import SSOP.ssop.dto.card.response.CardShareStatusResponse;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

    // 카드 생성
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
    @GetMapping("/view/saved")
    public ResponseEntity<List<CardResponse>> getSavedCards(@Login Long userId) {
        List<CardResponse> cards = cardService.getSavedCards(userId);
        return ResponseEntity.ok(cards);
    }

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
    public void updateCard(
            @Login Long userID,
            @RequestParam long cardId,
            @RequestPart(name = "card") CardUpdateRequest request,
            @RequestPart(name = "image", required = false) MultipartFile file
    ) throws URISyntaxException, IOException {
        request.setCard_id(cardId);
        cardService.updateCard(request, file);
        throw new CustomException(HttpStatus.OK, "카드가 수정되었습니다.");
    }

    // 카드 삭제 (내카드 & 상대카드)
    @DeleteMapping("/delete")
    public void deleteCard(@RequestParam List<Long> cardIds, @Login Long userId) throws URISyntaxException {
        List<Long> deletedCards = new ArrayList<>();
        List<Long> failedCards = new ArrayList<>();

        for (Long cardId : cardIds) {
            try {
                Card card = cardRepository.findById(cardId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다. ID: " + cardId));

                if (card.getUserId().equals(userId)) {
                    cardService.deleteCard(cardId, userId);
                    deletedCards.add(cardId);
                } else {
                    userService.deleteSavedCard(userId, List.of(cardId));
                    deletedCards.add(cardId);
                }
            } catch (Exception e) {
                failedCards.add(cardId);
                System.err.println("Failed to delete card ID: " + cardId + " - " + e.getMessage());
            }
        }

        if (!failedCards.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "카드 삭제에 실패했습니다. \n cardId: " + failedCards);
        }

        throw new CustomException(HttpStatus.OK, "카드를 삭제하였습니다.");
    }

    // 상대 카드 메모
    @PostMapping("/memo")
    public void writeMemo(@RequestParam long cardId, @Login Long userId, @RequestBody MemoRequest memo) {
        cardService.writeMemo(cardId, userId, memo.getMemo());
    }

    // 카드 공유 요청
    @PostMapping("/share")
    public ResponseEntity<Map<String, Object>> shareCard(@RequestBody CardShareRequest request, @Login Long userId) {
        try {
            // 카드 공유 서비스 호출
            CardShareResponse response = cardService.shareCard(userId, request.getCardId(), request.getRecipientId());

            // 성공적으로 카드 공유 시 응답
            return ResponseEntity.ok(Map.of(
                    "cardId", response.getCardId(),
                    "recipientId", response.getRecipientId(),
                    "message", "카드가 성공적으로 전송되었습니다.",
                    "status", response.getStatus()
            ));
        } catch (IllegalArgumentException e) {
            // 카드 ID나 수신자 ID가 잘못된 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (CustomException e) {
            // 인증되지 않은 사용자 토큰일 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효하지 않은 토큰입니다."));
        } catch (Exception e) {
            // 서버 내부 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }

    // 카드 공유 상태
    @GetMapping("/share/status/all")
    public ResponseEntity<List<CardShareStatusResponse>> getAllCardShareStatus(@Login Long userId) {
        try {
            // 서비스 호출하여 모든 공유 상태 조회
            List<CardShareStatusResponse> statuses = cardService.getAllCardShareStatus(userId);
            return ResponseEntity.ok(statuses);  // 성공 시 200 OK와 함께 공유 상태 목록 반환
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}