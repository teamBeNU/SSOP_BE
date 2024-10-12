package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.FilterMemberDto;
import SSOP.ssop.service.TeamSp.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filter")
public class FilterController {

    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    // 모든 조건에 대하여 한 번에 필터링 요청
    @GetMapping("/view")
    public List<FilterMemberDto> getMembersByFilters(
            @RequestParam Long teamId,
            @RequestParam(required = false) List<String> mbti,
            @RequestParam(required = false) List<String> role,
            @RequestParam(required = false) List<String> major,
            @RequestParam(required = false) List<String> template) {

        return filterService.getMembersByFilters(teamId, mbti, role, major, template);
    }
    
    // 팀 ID에 한정된 MBTI 목록
    @GetMapping("/list/mbti")
    public List<String> getMBTIFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardMBTIByTeamId(teamId);
    }

    // 팀 ID에 한정된 역할(role) 목록
    @GetMapping("/list/role")
    public List<String> getRoleFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardRoleByTeamId(teamId);
    }

    // 팀 ID에 한정된 전공(major) 목록
    @GetMapping("/list/major")
    public List<String> getMajorFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardMajorByTeamId(teamId);
    }

    // 팀 ID에 한정된 템플릿(template) 목록
    @GetMapping("/list/template")
    public List<String> getTemplateFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardTemplateByTeamId(teamId);
    }
}