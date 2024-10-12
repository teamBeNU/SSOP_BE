package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.dto.TeamSp.FilterMemberDto;
import SSOP.ssop.repository.TeamSp.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    private FilterRepository filterRepository;

    // 전체 필터링
    public List<FilterMemberDto> getMembersByFilters(Long teamId, List<String> mbti, List<String> role, List<String> major, List<String> template) {
        List<Member> members = filterRepository.findAllByFilters(teamId, mbti, role, major, template);

        return members.stream()
                .map(FilterMemberDto::new)
                .collect(Collectors.toList());
    }

    // MBTI 목록 받아오기
    public List<String> findDistinctCardMBTIByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> mbtiList = filterRepository.findDistinctCardMBTIByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return mbtiList.stream()
                .filter(mbti -> mbti != null && !mbti.trim().isEmpty())
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

    // Major 목록 받아오기
    public List<String> findDistinctCardMajorByTeamId(Long teamId) {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> majorList = filterRepository.findDistinctCardMajorByTeamId(teamId);

        // MBTI 값이 없는 항목 제거
        return majorList.stream()
                .filter(major -> major != null && !major.trim().isEmpty())
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
}
