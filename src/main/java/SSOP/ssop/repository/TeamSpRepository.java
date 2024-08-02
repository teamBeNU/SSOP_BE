package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface TeamSpRepository extends JpaRepository<TeamSp, Long> {

    Optional<TeamSp> findByTeamName(String teamName);
    Optional<TeamSp> findByTeamId(long team_id);

}
