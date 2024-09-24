package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.dto.TeamSp.EnterTeamSpDto;
import SSOP.ssop.dto.TeamSp.TeamSpInfoDto;
import SSOP.ssop.security.annotation.Login;
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
    @PostMapping("/create")
    public ResponseEntity<?> saveTeamSp(@RequestBody TeamSp teamSp, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다"));
            }

            ResponseEntity<?> inviteCodeResponse = teamSpService.saveTeamSp(teamSp, userId);
            return inviteCodeResponse;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "팀스페이스 생성 중 오류 발생"));
        }
    }

    // 초대코드로 스페이스 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchInviteCode(@RequestParam int inviteCode, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다."));
            }
            TeamSpInfoDto teamSpInfoDto = teamSpService.SearchInviteCode(inviteCode, userId);
            return ResponseEntity.ok(teamSpInfoDto); // 응답 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }
    // 팀 스페이스 입장
    @PostMapping("/enter")
    public ResponseEntity<?> enterTeamSp(@RequestBody EnterTeamSpDto enterTeamSpDto, @Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "유효한 토큰이 없습니다."));
            }
            TeamSpInfoDto teamSpInfoDto = teamSpService.EnterTeamSp(enterTeamSpDto.getInviteCode(), userId);
            return ResponseEntity.ok(teamSpInfoDto); // 응답 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
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
    public ResponseEntity<?> getTeamById(@RequestParam("teamId") Long teamId) {
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
    public ResponseEntity<Map<String, String>> updateTeamSp(@RequestParam("teamId") Long teamId, @RequestBody TeamSp teamSp, @Login Long userId) {
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
    public ResponseEntity<Map<String, String>> deleteTeamSp(@RequestParam("teamId") Long teamId, @Login Long userId) {
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