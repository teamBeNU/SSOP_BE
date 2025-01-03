package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.card.request.SubmitCardRequest;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.TeamSp.TeamSpMemberService;
import SSOP.ssop.dto.TeamSp.TeamSpMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/teamsp")
public class TeamSpMemberController {

    private final TeamSpMemberService teamSpMemberService;

    @Autowired
    public TeamSpMemberController(TeamSpMemberService teamSpMemberService) {
        this.teamSpMemberService = teamSpMemberService;
    }

    // 기존 카드 제출
    @PostMapping("/submit-card")
    public ResponseEntity<?> submitCard(@RequestParam Long teamId, @RequestBody SubmitCardRequest submitCardRequest, @Login Long userId) {
        try {
            Long cardId = submitCardRequest.getCardId();
            teamSpMemberService.SubmitCard(teamId, cardId, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "카드 ID " + cardId + "이(가) 제출되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // 모든 팀스페이스 참여 정보 조회
    @GetMapping("member/total")
    public ResponseEntity<?> getTeamMembers() {
        try {
            List<TeamSpMemberDto> teamSpMemberDto = teamSpMemberService.getTeamMembers();
            return ResponseEntity.ok(teamSpMemberDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }

    // 특정 팀스페이스 참여 정보 조회
    @GetMapping("/member")
    public ResponseEntity<?> getTeamMemberById(@RequestParam("teamId") Long teamId) {
        Optional<TeamSpMemberDto> teamSpMemberDto = teamSpMemberService.getTeamMemberById(teamId);

        if (teamSpMemberDto.isPresent()) {
            return ResponseEntity.ok(teamSpMemberDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "존재하지 않는 팀스페이스입니다."));
        }
    }
}