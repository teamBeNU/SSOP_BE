package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.dto.TeamSp.FilterMemberDto;
import SSOP.ssop.repository.TeamSp.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Stream.of(
                        filterRepository.findDistinctMemberMBTIByTeamId(teamId),
                        filterRepository.findDistinctCardMBTIByTeamId(teamId)
                )
                .flatMap(Collection::stream) // 두 리스트를 스트림으로 변환하여 병합
                .filter(Objects::nonNull) // null 값 필터링
                .collect(Collectors.toSet()) // 중복 제거를 위해 Set으로 수집
                .stream() // Set을 다시 스트림으로 변환
                .collect(Collectors.toList()); // 최종 리스트로 수집
    }

    // Role 목록 받아오기
    public List<String> findDistinctCardRoleByTeamId(Long teamId) {
        return Stream.of(
                filterRepository.findDistinctMemberRoleByTeamId(teamId),
                        filterRepository.findDistinctCardRoleByTeamId(teamId)
                )
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    // Major 목록 받아오기
    public List<String> findDistinctCardMajorByTeamId(Long teamId) {
        return Stream.of(
                        filterRepository.findDistinctMemberMajorByTeamId(teamId),
                        filterRepository.findDistinctCardMajorByTeamId(teamId)
                )
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    // Template 목록 받아오기
    public List<String> findDistinctCardTemplateByTeamId(Long teamId) {
        return Stream.of(
                        filterRepository.findDistinctMemberTemplateByTeamId(teamId),
                        filterRepository.findDistinctCardTemplateByTeamId(teamId)
                )
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
}
