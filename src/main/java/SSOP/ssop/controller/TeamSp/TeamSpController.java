package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.controller.Login;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.service.TeamSp.TeamSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teamsp")
public class TeamSpController {

    private final TeamSpService teamSpService;

    @Autowired
    public TeamSpController(TeamSpService teamSpService) {
        this.teamSpService = teamSpService;
    }

    // 팀스페이스 생성
    @Login
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> saveTeamSp(@RequestBody TeamSp teamSp, @RequestParam Long userId) {
        try {
            teamSpService.saveTeamSp(teamSp, userId); // 호스트 ID와 함께 저장
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "팀스페이스 생성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 생성 실패"));
        }
    }

    // 모든 팀스페이스 조회
    @GetMapping("/total")
    public ResponseEntity<List<TeamSp>> getAllTeams() {
        List<TeamSp> teams = teamSpService.getAllTeams();
        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(teams); // 200 OK
        }
    }

    // 특정 팀스페이스 조회
    @GetMapping
    public ResponseEntity<?> getTeamById(@RequestParam("team_id") long teamId) {
        TeamSp teamSp = teamSpService.getTeamById(teamId);

        if (teamSp != null) {
            return ResponseEntity.ok(teamSp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "팀스페이스를 찾을 수 없습니다."));
        }
    }

    // 팀스페이스 이름 수정 (호스트만)
    @Login
    @PatchMapping
    public ResponseEntity<Map<String, String>> updateTeamSp(@RequestParam("team_id") long teamId, @RequestBody TeamSp teamSp, @RequestParam Long userId) {
        try {
            TeamSp updatedTeamSp = teamSpService.updateTeamSp(teamId, teamSp, userId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스 이름 업데이트 완료"));
        } catch (IllegalArgumentException e) {
            // 팀스페이스를 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            // 권한이 없는 경우 또는 기타 런타임 예외
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 업데이트 실패: " + e.getMessage()));
        }
    }

    // 팀스페이스 삭제 (호스트-삭제 / 참여자-퇴장)
    @Login
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteTeamSp(@RequestParam("team_id") long teamId, @RequestParam Long userId) {
        try {
            teamSpService.deleteTeamSp(teamId, userId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스가 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "팀스페이스를 삭제하지 못하였습니다."));
        }
    }

}
