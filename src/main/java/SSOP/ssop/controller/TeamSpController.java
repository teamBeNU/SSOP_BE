package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.dto.EnterTeamSpDto;
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

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

        try {
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
    public ResponseEntity<Map<String, String>> enterTeamSp(@RequestBody EnterTeamSpDto enterTeamSpDto) {

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

        try {
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
    public ResponseEntity<?> getTeamById(@RequestParam("team_id") long teamId) {
        TeamSp teamSp = teamSpService.getTeamById(teamId);

        if (teamSp != null) {
            return ResponseEntity.ok(teamSp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "팀스페이스를 찾을 수 없습니다."));
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

    // 유저별 참여 중인 팀스페이스 정보 조회
    @GetMapping("user")
    public ResponseEntity<?> getTeamMemberByUserId(@RequestParam("userId") long userId) {
        try {
            List<TeamSpMemberDto> teamMemberDtos = teamSpService.getTeamMemberByUserId(userId);

            if (teamMemberDtos.isEmpty()) {
                return ResponseEntity.noContent().build(); // 참여 중인 팀스페이스가 없는 경우
            }
            return ResponseEntity.ok(teamMemberDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "팀스페이스 정보를 조회하는 도중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    // 팀스페이스 이름 수정 (team_id를 쿼리 파라미터로)
    @PatchMapping
    public ResponseEntity<Map<String, String>> updateTeamSp(@RequestParam("team_id") long teamId, @RequestBody TeamSp teamSp) {

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

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

    // 팀스페이스 삭제 (team_id를 쿼리 파라미터로)
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteTeamSp(@RequestParam("team_id") long teamId) {

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

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
