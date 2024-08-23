package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.dto.TeamSp.EnterTeamSpDto;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.service.TeamSp.TeamSpMemberService;
import SSOP.ssop.dto.TeamSp.TeamSpMemberDto;
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
public class TeamSpMemberController {

    private final TeamSpMemberService teamSpMemberService;

    @Autowired
    public TeamSpMemberController(TeamSpMemberService teamSpMemberService) {
        this.teamSpMemberService = teamSpMemberService;
    }

    // 팀 스페이스 입장
    @PostMapping("/enter")
    public ResponseEntity<Map<String, String>> enterTeamSp(@RequestBody EnterTeamSpDto enterTeamSpDto) {

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        long userId = ((UserDetail) userDetails).getUser().getUserId();

        try {
            teamSpMemberService.EnterTeamSp(enterTeamSpDto.getInviteCode(), userId);
            return ResponseEntity.ok(Map.of("message", "팀스페이스에 성공적으로 입장하였습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
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

//    // 특정 팀스페이스 참여 정보 조회 (team_id를 쿼리 파라미터로)
//    @GetMapping("/member")
//    public ResponseEntity<?> getTeamMemberById(@RequestParam("team_id") long teamId) {
//        Optional<TeamSpMemberDto> teamSpMemberDto = teamSpMemberService.getTeamMemberById(teamId);
//
//        if (teamSpMemberDto.isPresent()) {
//            return ResponseEntity.ok(teamSpMemberDto.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "존재하지 않는 팀스페이스입니다."));
//        }
//    }

    // 유저별 참여 중인 팀스페이스 정보 조회
    @GetMapping("user")
    public ResponseEntity<?> getTeamSpByUserId(@RequestParam("userId") long userId) {
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
