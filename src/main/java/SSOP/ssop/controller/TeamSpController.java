package SSOP.ssop.controller;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.service.TeamSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Map<String, String>> saveTeamSp(@RequestBody TeamSp teamSp) {
        try {
            teamSpService.saveTeamSp(teamSp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "팀스페이스 생성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 생성 실패"));
        }
    }

    // 모든 팀스페이스 조회
    @GetMapping
    public ResponseEntity<List<TeamSp>> getAllTeams() {
        List<TeamSp> teams = teamSpService.getAllTeams();
        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(teams); // 200 OK
        }
    }

    // 특정 팀스페이스 조회
    @GetMapping("/{team_id}")
    public ResponseEntity<?> getTeamById(@PathVariable("team_id") long teamId) {
        TeamSp teamSp = teamSpService.getTeamById(teamId);
        // 팀스페이스 존재 유무
        return teamSp != null
                ? ResponseEntity.ok(teamSp)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "존재하지 않는 팀스페이스입니다."));
    }

    // 특정 팀스페이스 참여 정보 조회
    @GetMapping("/{team_id}/member")
    public ResponseEntity<?> getTeamMemberById(@PathVariable("team_id") long teamId) {
        Optional<TeamSpMember> teamSpMember = teamSpService.getTeamMemberById(teamId);
        // 팀스페이스 존재 유무
        // Optional 객체가 값을 포함하고 있는지 확인
        return teamSpMember.isPresent()
                ? ResponseEntity.ok(teamSpMember.get())  // 값이 있을 경우 200 OK 응답
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "존재하지 않는 팀스페이스입니다."));  // 값이 없을 경우 404 NOT FOUND 응답
    }

    // 팀스페이스 이름 수정
    @PatchMapping("/{team_id}")
    public ResponseEntity<Map<String, String>> updateTeamSp(@PathVariable("team_id") long teamId, @RequestBody TeamSp teamSp) {
        TeamSp updatedTeamSp = teamSpService.updateTeamSp(teamId, teamSp);
        if (updatedTeamSp != null) {
            return ResponseEntity.ok(Map.of("message", "팀스페이스 이름 업데이트 완료"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 업데이트 실패"));
        }
    }

    // 팀스페이스 삭제
    @DeleteMapping("/{team_id}")
    public ResponseEntity<Map<String, String>> deleteTeamSp(@PathVariable("team_id") long teamId) {
        if (teamSpService.getTeamById(teamId) != null) {
            teamSpService.deleteTeamSp(teamId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스가 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "팀스페이스를 찾을 수 없습니다."));
        }
    }
}
