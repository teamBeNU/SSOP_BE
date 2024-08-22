package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamSpMemberRepository extends JpaRepository<TeamSpMember, Long> {
    
    // 팀스페이스 입장
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp.team_id = :teamSpId AND t.user.userId = :userId")
    Optional<TeamSpMember> findByTeamSpIdAndUserId(@Param("teamSpId") long teamSpId, @Param("userId") long userId);
    
    // 팀스페이스별 참여 정보 조회
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp.team_id = :team_id")
    List<TeamSpMember> findByTeamSpId(@Param("team_id") long teamId);

    // 유저별 참여 중인 팀스페이스 조회
    @Query("SELECT t FROM TeamSpMember t WHERE t.user.userId = :userId")
    List<TeamSpMember> findByUserId(@Param("userId") long userId);

    // 팀스페이스에서 나가기
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp = :teamSp AND t.user = :user")
    Optional<TeamSpMember> findByTeamSpAndUser(@Param("teamSp") TeamSp teamSp, @Param("user") User user);

    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp = :teamSp AND t.user.userId = :userId")
    List<TeamSpMember> findByTeamSpAndUserId(@Param("teamSp") TeamSp teamSp, @Param("userId") long userId);

    // 팀스페이스 id 찾기
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp.team_id = :teamId AND t.user.userId = :userId")
    Optional<Long> findIdByTeamSpIdAndUserId(@Param("teamId") long teamId, @Param("userId") long userId);

}
