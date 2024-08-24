package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
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
    public ResponseEntity<String> submitCard(@RequestParam Long teamId, @RequestBody Map<String, Long> requestBody, @Login Long userId) {
        try {
            Long cardId = requestBody.get("card_id");
            teamSpMemberService.SubmitCard(teamId, cardId, userId);
            String responseMessage = "카드 ID " + cardId + "이(가) 제출되었습니다.";
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    // 특정 팀스페이스 참여 정보 조회 (team_id를 쿼리 파라미터로)
    @GetMapping("/member")
    public ResponseEntity<?> getTeamMemberById(@RequestParam("team_id") Long teamId) {
        Optional<TeamSpMemberDto> teamSpMemberDto = teamSpMemberService.getTeamMemberById(teamId);

        if (teamSpMemberDto.isPresent()) {
            return ResponseEntity.ok(teamSpMemberDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "존재하지 않는 팀스페이스입니다."));
        }
    }

    // 유저별 참여 중인 팀스페이스 정보 조회
    @GetMapping("user")
    public ResponseEntity<?> getTeamSpByUserId(@RequestParam("userId") Long userId) {
        try {
            List<TeamSpByUserDto> teamSpByUserDto = teamSpMemberService.getTeamSpByUserId(userId);

            if (teamSpByUserDto.isEmpty()) {
                return ResponseEntity.noContent().build(); // 참여 중인 팀스페이스가 없는 경우
            }
            return ResponseEntity.ok(teamSpByUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "팀스페이스 정보를 조회하는 도중 오류가 발생했습니다: " + e.getMessage()));
        }
    }
}
