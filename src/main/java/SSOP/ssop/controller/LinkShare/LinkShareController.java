package SSOP.ssop.controller.LinkShare;

import SSOP.ssop.domain.link.LinkShare;
import SSOP.ssop.dto.LinkShare.LinkRequestDto;
import SSOP.ssop.dto.LinkShare.LinkResponseDto;
import SSOP.ssop.service.LinkShare.LinkShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/link")
public class LinkShareController {

    private final LinkShareService linkShareService;

    public LinkShareController(LinkShareService linkShareService) {
        this.linkShareService = linkShareService;
    }

    @PostMapping
    public ResponseEntity<?> createLink(@RequestBody LinkRequestDto request,  Authentication authentication) {
        LinkShare linkShare = linkShareService.createLink(request.getCard_id(), request.getExpiryTime());

        LinkResponseDto response = new LinkResponseDto(linkShare.getLink(), linkShare.getExpiryTime().toString());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}


