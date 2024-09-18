package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.dto.TeamSp.FilterDto;
import SSOP.ssop.repository.TeamSp.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    private FilterRepository filterRepository;

    // MBTI 목록 받아오기
    public List<String> findDistinctCardMBTIByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> mbtiList = filterRepository.findDistinctCardMBTIByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return mbtiList.stream()
                .filter(mbti -> mbti != null && !mbti.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // MBTI별 멤버 필터링
    public List<FilterDto> getMembersByMBTI(Long teamId, String mbti) {
        List<Member> filter = filterRepository.findByCardMBTIAndTeamId(mbti, teamId);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }

    // Role 목록 받아오기
    public List<String> findDistinctCardRoleByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> roleList = filterRepository.findDistinctCardRoleByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return roleList.stream()
                .filter(role -> role != null && !role.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // 역할별 멤버 필터링
    public List<FilterDto> getMembersByRole(Long teamId, String role) {
        List<Member> filter = filterRepository.findByCardRoleAndTeamId(role, teamId);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }

    // Major 목록 받아오기
    public List<String> findDistinctCardMajorByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> majorList = filterRepository.findDistinctCardMajorByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return majorList.stream()
                .filter(major -> major != null && !major.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // 전공별 멤버 필터링
    public List<FilterDto> getMembersByMajor(Long teamId, String major) {
        List<Member> filter = filterRepository.findByCardMajorAndTeamId(major, teamId);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }

    // Template 목록 받아오기
    public List<String> findDistinctCardTemplateByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> templateList = filterRepository.findDistinctCardTemplateByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return templateList.stream()
                .filter(template -> template != null && !template.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // 템플릿별 멤버 필터링
    public List<FilterDto> getMembersByTemplate(Long teamId, String template) {
        List<Member> filter = filterRepository.findByCardTemplateAndTeamId(template, teamId);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }
}
