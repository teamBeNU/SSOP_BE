package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.dto.TeamSp.EnterTeamSpDto;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.TeamSp.TeamSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> saveTeamSp(@RequestBody TeamSp teamSp, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다"));
            }
            teamSpService.saveTeamSp(teamSp, userId); // 호스트 ID와 함께 저장
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "팀스페이스 생성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 생성 실패"));
        }
    }

    // 팀 스페이스 입장
    @PostMapping("/enter")
    public ResponseEntity<Map<String, String>> enterTeamSp(@RequestBody EnterTeamSpDto enterTeamSpDto, @Login Long userId) {
        try {
            if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 토큰이 없습니다"));
            }
            teamSpService.EnterTeamSp(enterTeamSpDto.getInviteCode(), userId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스에 성공적으로 입장하였습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
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
    public ResponseEntity<?> getTeamById(@RequestParam("team_id") Long teamId) {
        TeamSp teamSp = teamSpService.getTeamById(teamId);

        if (teamSp != null) {
            return ResponseEntity.ok(teamSp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "팀스페이스를 찾을 수 없습니다."));
        }
    }

    // 팀스페이스 이름 수정 (호스트만)
    @PatchMapping
    public ResponseEntity<Map<String, String>> updateTeamSp(@RequestParam("team_id") Long teamId, @RequestBody TeamSp teamSp, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다"));
            }
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
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteTeamSp(@RequestParam("team_id") Long teamId, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다"));
            }
            teamSpService.deleteTeamSp(teamId, userId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스가 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "팀스페이스를 삭제하지 못하였습니다."));
        }
    }

}
