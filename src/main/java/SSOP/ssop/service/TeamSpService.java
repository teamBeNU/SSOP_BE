package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp;
import SSOP.ssop.repository.TeamSpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamSpService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    // 팀스페이스 생성
    public TeamSp saveTeamSp(TeamSp teamSp) {
        return teamSpRepository.save(teamSp);
    }

    // 모든 팀스페이스 Entity 조회
    public List<TeamSp> getAllTeams() {
        return teamSpRepository.findAll();
    }

    // 특정 id 팀스페이스 조회
    public TeamSp getTeamById(long id) {
        return teamSpRepository.findById(id).orElse(null);
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
