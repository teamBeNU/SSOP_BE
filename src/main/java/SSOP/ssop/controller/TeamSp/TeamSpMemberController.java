package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.dto.TeamSp.TeamSpMemSortedDto;
import SSOP.ssop.dto.card.request.SubmitCardRequest;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.SearchService;
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
    private final SearchService searchService;

    @Autowired
    public TeamSpMemberController(TeamSpMemberService teamSpMemberService, SearchService searchService) {
        this.teamSpMemberService = teamSpMemberService;
        this.searchService = searchService;
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

    // 최신순 정렬
    @GetMapping("/sort/recent")
    public ResponseEntity<?> getMembersByTeamIdSortedByRecent(@RequestParam Long teamId) {
        try {
            List<TeamSpMemSortedDto> teamSpMemSortedDto = teamSpMemberService.findByTeamSpIdSortedByRecent(teamId);
            return ResponseEntity.ok(teamSpMemSortedDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "팀스페이스 멤버를 최신순으로 정렬하는 도중 오류가 발생했습니다: " + e.getMessage()));
        }
    }

    // 이름순 정렬
    @GetMapping("/sort/name")
    public ResponseEntity<?> getMembersByTeamIdSortedByUsername(@RequestParam Long teamId) {
        try {
            List<TeamSpMemSortedDto> teamSpMemSortedDto = teamSpMemberService.findByTeamSpIdSortedByUsername(teamId);
            return ResponseEntity.ok(teamSpMemSortedDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "팀스페이스 멤버를 이름순으로 정렬하는 도중 오류가 발생했습니다: " + e.getMessage()));
        }
    }
}