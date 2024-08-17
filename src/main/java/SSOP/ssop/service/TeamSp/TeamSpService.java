package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.repository.TeamSpRepository;
import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.repository.TeamSpMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public TeamSp updateTeamSp(long id, TeamSp teamSp, long userId) {
        TeamSp existingTeamSp = teamSpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀스페이스를 찾을 수 없습니다."));

        if (existingTeamSp.getHostId() == userId) {
            existingTeamSp.updateTeamName(teamSp.getTeam_name());
            return teamSpRepository.save(existingTeamSp);
        } else {
            throw new RuntimeException("호스트만 팀스페이스 이름을 수정할 수 있습니다.");
        }
    }

    // 팀스페이스 삭제(호스트), 퇴장(참여자)
    public void deleteTeamSp(long team_id, long userId) {
        TeamSp teamSp = getTeamById(team_id);
        if (teamSp == null) {
            throw new IllegalArgumentException("팀스페이스를 찾을 수 없습니다.");
        }

        if (teamSp.getHostId() == userId) {
            teamSpRepository.delete(teamSp);
        } else {
            // 참여자 처리
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            TeamSpMember currentUserMember = teamSpMemberRepository.findByTeamSpAndUser(teamSp, user)
                    .orElseThrow(() -> new IllegalArgumentException("현재 사용자가 팀스페이스의 참여자가 아닙니다."));

            // 현재 사용자를 팀스페이스에서 제거
            teamSp.removeMember(currentUserMember);

            // 팀스페이스에서 다른 사용자 제거
            List<TeamSpMember> membersToRemove = teamSpMemberRepository.findByTeamSpAndUserId(teamSp, userId);
            for (TeamSpMember member : membersToRemove) {
                teamSp.removeMember(member);
            }

            teamSpRepository.save(teamSp);
        }
    }
}
