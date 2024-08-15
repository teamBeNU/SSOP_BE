package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

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
}
