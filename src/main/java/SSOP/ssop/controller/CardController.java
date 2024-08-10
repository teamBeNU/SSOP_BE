package SSOP.ssop.controller;

import SSOP.ssop.dto.request.CardCreateRequest;
import SSOP.ssop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public void saveCard(@RequestBody CardCreateRequest request) {
        cardService.saveCard(request);
    }
}
