package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.link.Link;
import SSOP.ssop.dto.ShareLinkRequestDto;
import SSOP.ssop.repository.LinkRepository;
import SSOP.ssop.service.LinkService;
import SSOP.ssop.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private LinkService linkService;
    private LinkRepository linkRepository;
    private UserDetailService userService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateLink() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(username);
        Link link = linkService.createLink("https://yourdomain.com/share", user);
        return ResponseEntity.ok(link);
    }

    @PostMapping("/share")
    public ResponseEntity<?> shareLink(@RequestBody ShareLinkRequestDto request) {
        // 링크 공유 기록 로직
        return ResponseEntity.ok("Link share action recorded.");
    }

    @GetMapping("/validate/{linkId}")
    public ResponseEntity<?> validateLink(@PathVariable String linkId) {
        Optional<Link> linkOptional = linkRepository.findByUrl("https://yourdomain.com/share/" + linkId);
        if (linkOptional.isPresent() && linkOptional.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
