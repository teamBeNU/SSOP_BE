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

    // team id를 통해 해당 팀스페이스의 멤버 카드 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.team_id = :teamId")
    List<Member> findByTeamId(@Param("teamId") long teamId);

    // user id가 가진 팀스페이스 멤버 카드 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.user.userId = :userId")
    List<Member> findByUserId(@Param("userId") long userId);

    // team id와 user id를 통해 특정 팀스페이스 멤버 카드 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.team_id = :teamId AND m.teamSpMember.user.userId = :userId")
    List<Member> findByTeamIdAndUserId(@Param("teamId") long teamId, @Param("userId") long userId);
}
