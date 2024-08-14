package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.dto.TeamSpMemberDto;
import SSOP.ssop.repository.TeamSpRepository;
import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.repository.TeamSpMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamSpService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamSpMemberRepository teamSpMemberRepository;

    // 팀스페이스 생성
    public void saveTeamSp(TeamSp teamSp, long hostId) {
        teamSp.setHostId(hostId); // 호스트 ID 저장
        teamSp.setInviteCode(createInviteCode()); // 초대코드 자동 생성
        teamSpRepository.save(teamSp);
    }

    // 랜덤 코드 생성
    private int createInviteCode() {
        Random random = new Random();
        int inviteCode;
        List<Integer> existingCodes = teamSpRepository.findAllInviteCodes();
        do {
            inviteCode = 100000 + random.nextInt(900000);
        } while (existingCodes.contains(inviteCode));
        return inviteCode;
    }

    // 모든 팀스페이스 Entity 조회
    public List<TeamSp> getAllTeams() {
        return teamSpRepository.findAll();
    }

    // 특정 id 팀스페이스 조회
    public TeamSp getTeamById(long team_id) {
        return teamSpRepository.findById(team_id).orElse(null);
    }

    // 팀스페이스 이름 수정
    public TeamSp updateTeamSp(long id, TeamSp teamSp) {
        TeamSp existingTeamSp = teamSpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀스페이스를 찾을 수 없습니다."));
        existingTeamSp.updateTeamName(teamSp.getTeam_name());
        return teamSpRepository.save(existingTeamSp);
    }

    // 팀스페이스 삭제
    public void deleteTeamSp(long team_id) {
        if (teamSpRepository.existsById(team_id)) {
            teamSpRepository.deleteById(team_id);
        }
    }

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
        // 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> new TeamSpMemberDto(
                        String.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                        entry.getValue()                 // 사용자 ID 목록
                ))
                .collect(Collectors.toList());
    }

    // 특정 id 팀스페이스 참여 정보 조회
    public Optional<TeamSpMemberDto> getTeamMemberById(long teamId) {
        // 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByTeamSpId(teamId);
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
                String.valueOf(teamId),
                userIds.isEmpty() ? Collections.emptyList() : userIds // 빈 배열로 반환
        );
        return Optional.of(teamSpMemberDto);
    }

    // 팀스페이트 나가기
    public void leaveTeamSp(long team_id, long userId) {
        TeamSpMember membership = teamSpMemberRepository.findByTeamSpIdAndUserId(team_id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Not a member of this team space"));

        teamSpMemberRepository.delete(membership);
    }
}
