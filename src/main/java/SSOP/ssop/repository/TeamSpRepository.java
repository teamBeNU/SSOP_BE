package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TeamSpRepository extends JpaRepository<TeamSp, Long> {

    Optional<TeamSp> findByTeamName(String teamName);

}
