package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.dto.TeamSpMemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamSpMemberRepository extends JpaRepository<TeamSpMember, Long> {
    
    // 팀스페이스 입장
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp.team_id = :teamSpId AND t.user.userId = :userId")
    Optional<TeamSpMember> findByTeamSpIdAndUserId(@Param("teamSpId") long teamSpId, @Param("userId") long userId);
    
    // 팀스페이스 참여 정보 조회
    @Query("SELECT t FROM TeamSpMember t WHERE t.teamSp.team_id = :teamId")
    List<TeamSpMember> findByTeamSpId(@Param("teamId") long teamId);
}
