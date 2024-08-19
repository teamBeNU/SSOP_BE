package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp.TeamSp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface TeamSpRepository extends JpaRepository<TeamSp, Long> {

    // 초대코드 생성
    @Query("SELECT t.inviteCode FROM TeamSp t")
    List<Integer> findAllInviteCodes();

    // inviteCode 팀스페이스 조회
    Optional<TeamSp> findByInviteCode(int inviteCode);

//    Optional<TeamSp> findByTeamName(String team_name);

}
