package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp;
import SSOP.ssop.dto.TeamSpDto;
import SSOP.ssop.repository.TeamSpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamSpService {

    private final TeamSpRepository teamSpRepository;

    @Autowired
    public TeamSpService(TeamSpRepository repository, TeamSpRepository teamSpRepository) {
        this.teamSpRepository = teamSpRepository;
    }

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
    public void updateTeamSp(TeamSpDto teamSpDto) {
        TeamSp teamSp = teamSpRepository.findById(teamSpDto.getTeam_id())
                .orElseThrow(IllegalAccessError::new);
        teamSp.updateTeamName(teamSpDto.getTeam_name());
        teamSpRepository.save(teamSp);
    }

    // 팀스페이스 삭제
    public void deleteTeamSp(long id) {
        if (teamSpRepository.existsById(id)) {
            teamSpRepository.deleteById(id);
        }
    }
}
