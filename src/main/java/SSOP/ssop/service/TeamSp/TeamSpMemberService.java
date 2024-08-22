package SSOP.ssop.service.TeamSp;

import SSOP.ssop.controller.Login;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.dto.card.TeamSp.TeamSpMemberDto;
import SSOP.ssop.repository.TeamSpMemberRepository;
import SSOP.ssop.repository.TeamSpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamSpMemberService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamSpMemberRepository teamSpMemberRepository;

    // 팀스페이스 입장
    public void EnterTeamSp(int inviteCode, long userId) {
        // 1. 팀스페이스 정보 가져오기
        TeamSp teamSp = teamSpRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대 코드입니다."));

        // 2. 유저 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 3. 호스트가 아닌지 확인
        if (teamSp.getHostId() == userId) {
            throw new IllegalArgumentException("호스트는 팀스페이스에 입장할 수 없습니다.");
        }

        // 4. 이미 입장한 사용자인지 종복 확인
        Optional<TeamSpMember> existingMembership = teamSpMemberRepository.findByTeamSpIdAndUserId(teamSp.getTeam_id(), userId);
        if (existingMembership.isPresent()) {
            throw new IllegalArgumentException("이미 입장한 팀스페이스입니다.");
        }

        // 5. 유저 정보에서 팀스페이스 추가
        user.enterTeamSp(teamSp);

        // 6. 팀스페이스 멤버 저장
        teamSpMemberRepository.saveAll(user.getTeamSpMembers());
    }

    // 팀스페이스 참여 정보 조회
    public List<TeamSpMemberDto> getTeamMembers() {
        // 모든 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findAll();

        // 팀 ID와 사용자 ID 목록으로 그룹화
        Map<Long, List<Long>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeam_id(),
                        Collectors.mapping(
                                member -> member.getUser().getUserId(),
                                Collectors.toList()
                        )
                ));

        // 2. 팀스페이스 ID 목록으로 팀 이름과 팀 설명을 가져오는 맵
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TeamSp::getTeam_id,
                        teamSp -> teamSp // 팀 객체 자체를 맵에 저장
                ));

        // 3. 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> {
                    TeamSp teamSp = teamSpMap.get(entry.getKey());
                    return new TeamSpMemberDto(
                            String.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                            teamSp.getTeam_name(),           // 팀 이름
                            teamSp.getTeam_comment(),        // 팀 설명
                            entry.getValue()                 // 사용자 ID 목록
                    );
                })
                .collect(Collectors.toList());
    }

    // 특정 id 팀스페이스 참여 정보 조회
    public Optional<TeamSpMemberDto> getTeamMemberById(long team_id) {
        // 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByTeamSpId(team_id);

        // 팀스페이스 정보를 조회
        TeamSp teamSp = teamSpRepository.findById(team_id)
                .orElseThrow(() -> new IllegalArgumentException("팀스페이스를 찾을 수 없습니다."));

        // 멤버 정보가 없으면 빈 Optional 반환
        if (members.isEmpty()) {
            return Optional.empty();
        }

        // 팀스페이스 ID와 사용자 ID 목록으로 변환
        List<Long> userIds = members.stream()
                .map(member -> member.getUser().getUserId())
                .collect(Collectors.toList());

        // DTO 객체 생성
        TeamSpMemberDto teamSpMemberDto = new TeamSpMemberDto(
                String.valueOf(team_id),
                teamSp.getTeam_name(), // 팀 이름
                teamSp.getTeam_comment(),
                userIds.isEmpty() ? Collections.emptyList() : userIds // 사용자 ID 목록
        );
        return Optional.of(teamSpMemberDto);
    }

    // 유저별 참여 중인 팀스페이스 정보 조회
    public List<TeamSpByUserDto> getTeamSpByUserId(long userId) {
        // 유저가 참여 중인 팀스페이스 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByUserId(userId);

        // 멤버 정보가 없으면 빈 리스트 반환
        if (members.isEmpty()) {
            return Collections.emptyList();
        }
        // 팀스페이스 ID 목록으로 변환
        Map<Long, List<Long>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeam_id(),
                        Collectors.mapping(
                                member -> member.getUser().getUserId(),
                                Collectors.toList()
                        )
                ));
        // 팀 ID와 팀 이름 및 팀 설명을 조회
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TeamSp::getTeam_id,
                        teamSp -> teamSp // 팀 객체 자체를 맵에 저장
                ));

        // 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> {
                    TeamSp teamSp = teamSpMap.get(entry.getKey());
                    return new TeamSpByUserDto(
                            String.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                            teamSp.getTeam_name(),           // 팀 이름
                            teamSp.getTeam_comment()       // 팀 설명
                    );
                })
                .collect(Collectors.toList());
    }
}
