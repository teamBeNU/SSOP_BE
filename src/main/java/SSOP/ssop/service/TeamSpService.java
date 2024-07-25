package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp;
import SSOP.ssop.repository.TeamSpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamSpService {

    private final TeamSpRepository repository;

    @Autowired
    public TeamSpService(TeamSpRepository repository) {
        this.repository = repository;
    }

    public TeamSp saveTeamSp(TeamSp teamSp) {
        return repository.save(teamSp);
    }

    // 모든 팀스페이스 Entity 조회
    public List<TeamSp> getAllTeams() {
        return repository.findAll();
    }

    // 특정 id 팀스페이스 조회
    public TeamSp getTeamById(long id) {
        return repository.findById(id).orElse(null);
    }

    // 팀스페이스 삭제
    public void deleteTeamSp(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
