package SSOP.ssop.controller.TeamSp;

import SSOP.ssop.dto.TeamSp.FilterDto;
import SSOP.ssop.service.TeamSp.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filter")
public class FilterController {

    private FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    // MBTI 리스트 목록
    @GetMapping("/list/mbti")
    public List<String> getMBTIFilters() {
        return filterService.getDistinctMBTIList();
    }

    // MBTI 필터링
    @GetMapping("/mbti")
    public List<FilterDto> getMembersByMBTI(@RequestParam String mbti) {
        return filterService.getMembersByMBTI(mbti);
    }

    // 역할(role) 리스트 목록
    @GetMapping("/list/role")
    public List<String> getRoleFilters() {
        return filterService.getDistinctRoleList();
    }

    // 역할(role) 필터링
    @GetMapping("/role")
    public List<FilterDto> getMembersByRole(@RequestParam String role) {
        return filterService.getMembersByRole(role);
    }

    // 전공(major) 리스트 목록
    @GetMapping("/list/major")
    public List<String> getMajorFilters() {
        return filterService.getDistinctMajorList();
    }

    // 전공(major) 필터링
    @GetMapping("/major")
    public List<FilterDto> getMembersByMajor(@RequestParam String major) {
        return filterService.getMembersByMajor(major);
    }
}
