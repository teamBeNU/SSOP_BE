package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.MemberRequest;
import SSOP.ssop.dto.TeamSp.MemberResponse;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.TeamSp.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teamsp/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 팀스페이스 멤버 카드 생성
    @PostMapping("/create/{teamId}")
    public ResponseEntity<Map<String, Object>> saveMember(
            @Login Long userId,
            @PathVariable("teamId") Long teamId,
            @RequestPart("member") MemberRequest memberDto,
            @RequestPart(name = "image", required = false) MultipartFile file
    ) {
        try {
            // 팀스페이스 멤버 카드 생성 서비스 호출
            memberService.saveMember(teamId, userId, memberDto, file);
            return ResponseEntity.ok(Map.of("code", 200, "message", "팀스페이스 멤버 카드 생성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
    }

    // 특정 팀스페이스의 모든 멤버 카드 조회
    @GetMapping("/total/view")
    public ResponseEntity<List<MemberResponse>> getAllMembers(@RequestParam("teamId") Long teamId) {
        List<MemberResponse> members = memberService.getAllMembers(teamId);
        return ResponseEntity.ok(members);
    }

    // 특정 팀스페이스의 특정 멤버 카드 조회
    @GetMapping("/view")
    public ResponseEntity<List<MemberResponse>> getMember(@RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
        List<MemberResponse> member = memberService.getMember(teamId, userId);
        return ResponseEntity.ok(member);
    }
}
