package SSOP.ssop.controller;

import SSOP.ssop.domain.bluetooth.Recipient;
import SSOP.ssop.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 수신자 리스트
    @GetMapping
    public ResponseEntity<List<Recipient>> getRecipients() {
        List<Recipient> recipients = recipientService.getAllRecipients();
        return ResponseEntity.ok(recipients);
    }
}
