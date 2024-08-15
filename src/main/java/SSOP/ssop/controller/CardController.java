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

//    @PostMapping("/create")
//    public void saveCard(@RequestBody CardCreateRequest request) {
////        // Authentication 객체에서 사용자 정보 추출
////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////        String userId = userDetails.getUsername();
//
////        cardService.saveCard(request);
//
//        // 현재 인증된 사용자 정보를 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//
//        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
//
//        Long authenticatedUserId = userDetail.getUser().getUserId();
//
//        cardService.saveCard(request, authenticatedUserId);
//
//
//
////        // 인증된 사용자 ID와 요청된 사용자 ID가 일치하는지 확인
////        if (authenticatedUserId.equals(userId)) {
////            userDto.setUserId(userId);
////            return userService.updatePhone(userDto);
////        } else {
////            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "권한이 없습니다."));
////        }
//    }

    @PostMapping("/create")
    public ResponseEntity<String> saveCard(@RequestBody CardCreateRequest request) {

    // 현재 인증된 사용자의 정보를 가져옴
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetail userDetail = (UserDetail) authentication.getPrincipal();

    // 인증된 사용자의 userId 가져오기
    Long authenticatedUserId = userDetail.getUser().getUserId();

    // 카드 생성 서비스 호출
    cardService.saveCard(request, authenticatedUserId);

    return ResponseEntity.ok("Card created successfully");
}

}
