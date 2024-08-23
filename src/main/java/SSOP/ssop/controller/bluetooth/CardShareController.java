package SSOP.ssop.controller.bluetooth;

import SSOP.ssop.domain.bluetooth.CardShareRequest;
import SSOP.ssop.dto.ShareRequestDto;
import SSOP.ssop.service.CardShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards/share")
public class CardShareController {

    private final CardShareService cardShareService;

    @Autowired
    public CardShareController(CardShareService cardShareService) {
        this.cardShareService = cardShareService;
    }

    // 카드 요청
    @PostMapping
    public ResponseEntity<CardShareRequest> shareCard(@RequestBody ShareRequestDto requestDto) {
        CardShareRequest shareRequest = cardShareService.createShareRequest(requestDto.getCardId(), requestDto.getRecipientId());
        return ResponseEntity.ok(shareRequest);
    }

    // 카드 공유 상태
    @GetMapping("/status/{recipientId}")
    public ResponseEntity<List<CardShareRequest>> getShareStatus(@PathVariable Long recipientId) {
        List<CardShareRequest> shareRequests = cardShareService.getShareRequestsByRecipientId(recipientId);
        return ResponseEntity.ok(shareRequests);
    }
}
