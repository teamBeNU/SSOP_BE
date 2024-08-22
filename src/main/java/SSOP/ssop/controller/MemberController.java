package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.dto.card.TeamSp.MemberDto;
import SSOP.ssop.dto.card.request.CardCreateRequest;
import SSOP.ssop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/teamsp/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> saveMember(
            @RequestParam("teamId") Long teamId,
            @RequestPart("member") MemberDto memberDto,
            @RequestPart(name = "image", required = false) MultipartFile file
    ) {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        // 인증된 사용자의 userId 가져오기
        Long authenticatedUserId = userDetail.getUser().getUserId();

        try {
            // 팀스페이스 멤버 카드 생성 서비스 호출
            memberService.saveMember(teamId, authenticatedUserId, memberDto, file);
            return ResponseEntity.ok(Map.of("code", 200, "message", "팀스페이스 멤버 카드 생성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
    }
}
