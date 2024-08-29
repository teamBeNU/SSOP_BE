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
    public List<String> getDistinctMBTIList() {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> mbtiList = filterRepository.findDistinctCardMBTI();

        // MBTI 값이 없는 항목 제거
        return mbtiList.stream()
                .filter(mbti -> mbti != null && !mbti.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // MBTI별 멤버 필터링
    public List<FilterDto> getMembersByMBTI(String mbti) {
        List<Member> filter = filterRepository.findByCardMBTI(mbti);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }

    // Role 목록 받아오기
    public List<String> getDistinctRoleList() {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> roleList = filterRepository.findDistinctCardRole();

        // MBTI 값이 없는 항목 제거
        return roleList.stream()
                .filter(role -> role != null && !role.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // 역할별 멤버 필터링
    public List<FilterDto> getMembersByRole(String role) {
        List<Member> filter = filterRepository.findByCardRole(role);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }

    // Major 목록 받아오기
    public List<String> getDistinctMajorList() {

        // DB에서 유일한 MBTI 값 목록 가져오기
        List<String> majorList = filterRepository.findDistinctCardMajor();

        // MBTI 값이 없는 항목 제거
        return majorList.stream()
                .filter(major -> major != null && !major.trim().isEmpty())
                .collect(Collectors.toList());
    }

    // 전공별 멤버 필터링
    public List<FilterDto> getMembersByMajor(String major) {
        List<Member> filter = filterRepository.findByCardMajor(major);
        return filter.stream()
                .map(FilterDto::new)
                .collect(Collectors.toList());
    }
}
