package SSOP.ssop.controller.LinkShare;

import SSOP.ssop.controller.CustomException;
import SSOP.ssop.domain.link.LinkShare;
import SSOP.ssop.dto.LinkShare.LinkRequestDto;
import SSOP.ssop.dto.LinkShare.LinkResponseDto;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.LinkShare.LinkShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/link")
public class LinkShareController {

    private final LinkShareService linkShareService;

    public LinkShareController(LinkShareService linkShareService) {
        this.linkShareService = linkShareService;
    }

    // 링크 생성
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createShareLink(
            @RequestBody Map<String, Long> requestBody, @Login Long userId) {
        Long cardId = requestBody.get("cardId");

        try {
            // 카드 링크 생성
            String link = linkShareService.createShareLink(cardId, userId);
            return ResponseEntity.ok(Map.of(
                    "link", link,
                    "cardId", cardId,
                    "expiryTime", 600,    // 10분 = 600초
                    "message", "링크가 성공적으로 생성되었습니다."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "링크 생성 중 오류가 발생했습니다."));
        }
    }

    // 링크 카드 정보 확인
    @GetMapping("/{token}")
    public ResponseEntity<?> getCardInfoFromLink(@PathVariable String token) {
        try {
            CardResponse card = linkShareService.getCardInfoFromToken(token);
            return ResponseEntity.ok(card);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "링크가 만료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "카드 정보를 가져오는 중 오류가 발생했습니다."));
        }
    }
}


