package SSOP.ssop.controller;

import SSOP.ssop.domain.TeamSp;
import SSOP.ssop.service.TeamSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String, String>> createTeamSp(@RequestBody TeamSp teamSp) {
        TeamSp savedTeamSp = teamSpService.saveTeamSp(teamSp);
        if (savedTeamSp != null) {
            // 저장 성공
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("message", "팀스페이스 생성"));
        } else {
            // 저장 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 생성 실패"));
        }
    }

    // 팀스페이스 조회
    @GetMapping("/{team_id}")
    public ResponseEntity<TeamSp> getTeamById(@PathVariable("team_id") long teamId) {
        TeamSp teamSp = teamSpService.getTeamById(teamId);
        if (teamSp != null) {
            // 팀스페이스가 존재할 경우
            return ResponseEntity.status(HttpStatus.OK).body(teamSp);
        } else {
            // 팀스페이스가 존재하지 않을 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
