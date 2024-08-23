package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 팀스페이스 id 찾기
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.id  = :teamspMemberId")
    Optional<Member> findByTeamSpMemberId(@Param("teamspMemberId") Long teamspMemberId);

    // teamSpMemberId로 멤버가 존재하는지 확인하는 메서드
    boolean existsByTeamSpMemberId(Long teamspMemberId);

    // 해당 팀스페이스의 멤버 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.team_id = :teamId")
    List<Member> findByTeamId(@Param("teamId") long teamId);

}
