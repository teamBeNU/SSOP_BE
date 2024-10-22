package SSOP.ssop.controller;

import SSOP.ssop.dto.Search.KeywordDto;
import SSOP.ssop.dto.Search.SearchDto;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("api/card/search")
    public ResponseEntity<SearchDto> search(
            @Login Long userId,
            @RequestBody KeywordDto keywordDto) {
        // 검색어 유효성 검사
        if (keywordDto == null || keywordDto.getKeyword() == null || keywordDto.getKeyword().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "검색어를 입력해 주세요."); // 유효하지 않은 검색어
        }

        String keyword = keywordDto.getKeyword().trim(); // 검색어 공백 제거

        // 카드, 멤버 통합 검색
        SearchDto searchDto = searchService.searchByKeyword(userId, keyword);

        // 검색 결과가 비어 있는 경우 예외 처리
        if (searchDto.getSavedCardSearchDto().isEmpty() &&
                searchDto.getTeamSpSearchDto().isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "해당 검색어에 맞는 결과를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(searchDto); // 검색 결과 반환
    }

    // 유저별 참여 중인 팀스페이스 정보 조회
    @GetMapping("api/teamsp/user")
    public ResponseEntity<?> getTeamSpByUserId(@RequestParam("userId") Long userId) {
        try {
            List<TeamSpByUserDto> teamSpByUserDto = searchService.getTeamSpByUserId(userId);

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