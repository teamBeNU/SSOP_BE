package SSOP.ssop.repository.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilterRepository extends JpaRepository<Member, Long> {

    // MBTI 목록
    @Query("SELECT DISTINCT m.card_MBTI FROM Member m WHERE m.teamSpMember.teamSp.teamId = :teamId AND m.card_MBTI IS NOT NULL")
    List<String> findDistinctMemberMBTIByTeamId(@Param("teamId") Long teamId);
    @Query("SELECT DISTINCT c.card_MBTI FROM Card c WHERE c.cardId IN (SELECT t.cardId FROM TeamSpMember t WHERE t.teamSp.teamId = :teamId) AND c.card_MBTI IS NOT NULL")
    List<String> findDistinctCardMBTIByTeamId(@Param("teamId") Long teamId);

    // 역할(role) 목록
    @Query("SELECT DISTINCT m.card_student_role FROM Member m WHERE m.card_student_role IS NOT NULL AND m.teamSpMember.teamSp.teamId = :teamId")
    List<String> findDistinctMemberRoleByTeamId(@Param("teamId") Long teamId);
    @Query("SELECT DISTINCT c.cardStudent.card_student_role FROM Card c WHERE c.cardId IN (SELECT t.cardId FROM TeamSpMember t WHERE t.teamSp.teamId = :teamId) AND c.cardStudent.card_student_role IS NOT NULL")
    List<String> findDistinctCardRoleByTeamId(@Param("teamId") Long teamId);

    // 전공(major) 목록
    @Query("SELECT DISTINCT m.card_student_major FROM Member m WHERE m.card_student_major IS NOT NULL AND m.teamSpMember.teamSp.teamId = :teamId")
    List<String> findDistinctMemberMajorByTeamId(@Param("teamId") Long teamId);
    @Query("SELECT DISTINCT c.cardStudent.card_student_major FROM Card c WHERE c.cardId IN (SELECT t.cardId FROM TeamSpMember t WHERE t.teamSp.teamId = :teamId) AND c.cardStudent.card_student_major IS NOT NULL")
    List<String> findDistinctCardMajorByTeamId(@Param("teamId") Long teamId);

    // 템플릿(template) 목록
    @Query("SELECT DISTINCT m.card_template FROM Member m WHERE m.card_template IS NOT NULL AND m.teamSpMember.teamSp.teamId = :teamId")
    List<String> findDistinctMemberTemplateByTeamId(@Param("teamId") Long teamId);
    @Query("SELECT DISTINCT c.card_template FROM Card c WHERE c.cardId IN (SELECT t.cardId FROM TeamSpMember t WHERE t.teamSp.teamId = :teamId) AND c.card_template IS NOT NULL")
    List<String> findDistinctCardTemplateByTeamId(@Param("teamId") Long teamId);

    // 모든 조건에 대해 한번에 필터링 요청
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.teamId = :teamId " +
            "AND (:mbti IS NULL OR m.card_MBTI IN :mbti) " +
            "AND (:role IS NULL OR m.card_student_role IN :role) " +
            "AND (:major IS NULL OR m.card_student_major IN :major) " +
            "AND (:template IS NULL OR m.card_template IN :template)")
    List<Member> findAllByFilters(
            @Param("teamId") Long teamId,
            @Param("mbti") List<String> mbti,
            @Param("role") List<String> role,
            @Param("major") List<String> major,
            @Param("template") List<String> template);

    @Query("SELECT c FROM Card c WHERE c.cardId IN (SELECT t.cardId FROM TeamSpMember t WHERE t.teamSp.teamId = :teamId) " +
            "AND (:mbti IS NULL OR c.card_MBTI IN :mbti) " +
            "AND (:role IS NULL OR c.cardStudent.card_student_role IN :role) " +
            "AND (:major IS NULL OR c.cardStudent.card_student_major IN :major) " +
            "AND (:template IS NULL OR c.card_template IN :template)")
    List<Card> findAllByCardFilters(
            @Param("teamId") Long teamId,
            @Param("mbti") List<String> mbti,
            @Param("role") List<String> role,
            @Param("major") List<String> major,
            @Param("template") List<String> template);

}