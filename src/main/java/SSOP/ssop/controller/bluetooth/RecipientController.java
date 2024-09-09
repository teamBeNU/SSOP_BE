package SSOP.ssop.controller.bluetooth;

import SSOP.ssop.dto.Bluetooth.RecipientResponse;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.Bluetooth.RecipientService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    // 수신자 리스트 조회
    @GetMapping
    public ResponseEntity<List<RecipientResponse>> getRecipients(
            @RequestParam(required = false) String status,
            @Login Long userId) {  // @Login으로 로그인된 사용자 ID 가져옴
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);  // 유효하지 않은 토큰일 경우 처리
        }

        List<RecipientResponse> recipients = recipientService.getRecipients(status, userId);
        return ResponseEntity.ok(recipients);  // 성공 시 200 OK와 함께 수신자 목록 반환
    }
}
