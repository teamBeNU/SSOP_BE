package SSOP.ssop.controller;

import SSOP.ssop.dto.Search.KeywordDto;
import SSOP.ssop.dto.Search.SearchDto;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.SearchService;
import SSOP.ssop.service.User.UserService;
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
        String keyword = keywordDto.getKeyword();

        // 사용자 저장 카드 ID 목록 가져오기
        List<Long> savedCardIds = searchService.getSavedCardIds(userId);

        // 검색 서비스 호출, 저장된 카드 ID 목록 함께 전달
        SearchDto searchDto = searchService.searchByKeyword(userId, keyword);

        // 검색 결과가 비어있는 경우
        if (searchDto.getCardSearchDto().isEmpty() && searchDto.getMemberSearchDto().isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "해당 검색어에 맞는 카드를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(searchDto);
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