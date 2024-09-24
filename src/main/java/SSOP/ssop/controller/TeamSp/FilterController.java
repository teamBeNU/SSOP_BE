package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.FilterDto;
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

    // 팀 ID에 한정된 MBTI 목록
    @GetMapping("/list/mbti")
    public List<String> getMBTIFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardMBTIByTeamId(teamId);
    }

    // 팀 ID와 MBTI에 한정된 멤버 필터링
    @GetMapping("/mbti")
    public List<FilterDto> getMembersByMBTI(@RequestParam Long teamId, @RequestParam String mbti) {
        return filterService.getMembersByMBTI(teamId, mbti);
    }

    // 팀 ID에 한정된 역할(role) 목록
    @GetMapping("/list/role")
    public List<String> getRoleFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardRoleByTeamId(teamId);
    }

    // 팀 ID와 역할(role)에 한정된 멤버 필터링
    @GetMapping("/role")
    public List<FilterDto> getMembersByRole(@RequestParam Long teamId, @RequestParam String role) {
        return filterService.getMembersByRole(teamId, role);
    }

    // 팀 ID에 한정된 전공(major) 목록
    @GetMapping("/list/major")
    public List<String> getMajorFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardMajorByTeamId(teamId);
    }

    // 팀 ID와 전공(major)에 한정된 멤버 필터링
    @GetMapping("/major")
    public List<FilterDto> getMembersByMajor(@RequestParam Long teamId, @RequestParam String major) {
        return filterService.getMembersByMajor(teamId, major);
    }

    // 팀 ID에 한정된 템플릿(template) 목록
    @GetMapping("/list/template")
    public List<String> getTemplateFilters(@RequestParam Long teamId) {
        return filterService.findDistinctCardTemplateByTeamId(teamId);
    }

    // 팀 ID와 전공(major)에 한정된 멤버 필터링
    @GetMapping("/template")
    public List<FilterDto> getMembersByTemplate(@RequestParam Long teamId, @RequestParam String template) {
        return filterService.getMembersByTemplate(teamId, template);
    }
}
