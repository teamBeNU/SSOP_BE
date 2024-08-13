package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.dto.TeamSpMemberDto;
import SSOP.ssop.service.TeamSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // 팀 스페이스 입장
    @PostMapping("/enter")
    public ResponseEntity<Map<String, String>> enterTeamSp(@RequestParam int inviteCode) {

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

        try {
            teamSpService.EnterTeamSp(inviteCode, userId);
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

    // 모든 팀스페이스 참여 정보 조회
    @GetMapping("member/total")
    public ResponseEntity<?> getTeamMembers() {
        try {
            List<TeamSpMemberDto> teamSpMemberDto = teamSpService.getTeamMembers();
            return ResponseEntity.ok(teamSpMemberDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "서버 오류가 발생했습니다."));
        }
    }

    // 특정 팀스페이스 참여 정보 조회 (team_id를 쿼리 파라미터로)
    @GetMapping("/member")
    public ResponseEntity<?> getTeamMemberById(@RequestParam("team_id") long teamId) {
        Optional<TeamSpMemberDto> teamSpMemberDto = teamSpService.getTeamMemberById(teamId);

        if (teamSpMemberDto.isPresent()) {
            return ResponseEntity.ok(teamSpMemberDto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "존재하지 않는 팀스페이스입니다."));
        }
    }

    // 팀스페이스 이름 수정 (team_id를 쿼리 파라미터로)
    @PatchMapping
    public ResponseEntity<Map<String, String>> updateTeamSp(@RequestParam("team_id") long teamId, @RequestBody TeamSp teamSp) {
        TeamSp updatedTeamSp = teamSpService.updateTeamSp(teamId, teamSp);
        if (updatedTeamSp != null) {
            return ResponseEntity.ok(Map.of("message", "팀스페이스 이름 업데이트 완료"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "팀스페이스 업데이트 실패"));
        }
    }

    // 팀스페이스 삭제 (team_id를 쿼리 파라미터로)
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteTeamSp(@RequestParam("team_id") long teamId) {
        if (teamSpService.getTeamById(teamId) != null) {
            teamSpService.deleteTeamSp(teamId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스가 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "팀스페이스를 찾을 수 없습니다."));
        }
    }

}
