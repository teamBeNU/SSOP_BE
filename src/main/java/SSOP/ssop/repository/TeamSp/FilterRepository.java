package SSOP.ssop.repository.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilterRepository extends JpaRepository<Member, Long> {

    // MBTI 목록
    @Query("SELECT DISTINCT m.card_MBTI FROM Member m WHERE m.card_MBTI IS NOT NULL AND m.teamSpMember.teamSp.team_id = :teamId")
    List<String> findDistinctCardMBTIByTeamId(@Param("teamId") Long teamId);

    // MBTI 필터링
    @Query("SELECT m FROM Member m WHERE m.card_MBTI = :mbti AND m.teamSpMember.teamSp.team_id = :teamId")
    List<Member> findByCardMBTIAndTeamId(@Param("mbti") String mbti, @Param("teamId") Long teamId);

    // 역할(role) 목록
    @Query("SELECT DISTINCT m.card_student_role FROM Member m WHERE m.card_student_role IS NOT NULL AND m.teamSpMember.teamSp.team_id = :teamId")
    List<String> findDistinctCardRoleByTeamId(@Param("teamId") Long teamId);

    // 역할(role) 필터링
    @Query("SELECT m FROM Member m WHERE m.card_student_role = :role AND m.teamSpMember.teamSp.team_id = :teamId")
    List<Member> findByCardRoleAndTeamId(@Param("role") String role, @Param("teamId") Long teamId);

    // 전공(major) 목록
    @Query("SELECT DISTINCT m.card_student_major FROM Member m WHERE m.card_student_major IS NOT NULL AND m.teamSpMember.teamSp.team_id = :teamId")
    List<String> findDistinctCardMajorByTeamId(@Param("teamId") Long teamId);

    // 전공(major) 필터링
    @Query("SELECT m FROM Member m WHERE m.card_student_major = :major AND m.teamSpMember.teamSp.team_id = :teamId")
    List<Member> findByCardMajorAndTeamId(@Param("major") String major, @Param("teamId") Long teamId);
}
