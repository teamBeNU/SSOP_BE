package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.repository.TeamSpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.List;

@Service
public class TeamSpService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    private static final SecureRandom random = new SecureRandom();
    private static final int MIN_CODE = 100000;
    private static final int MAX_CODE = 999999;

    // 팀스페이스 생성
    public void saveTeamSp(TeamSp teamSp) {
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
}
