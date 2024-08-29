package SSOP.ssop.repository.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilterRepository extends JpaRepository<Member, Long> {

    // MBTI 목록
    @Query("SELECT DISTINCT m.card_MBTI FROM Member m WHERE m.card_MBTI IS NOT NULL")
    List<String> findDistinctCardMBTI();

    // MBTI 필터링
    @Query("SELECT m FROM Member m WHERE m.card_MBTI = :mbti")
    List<Member> findByCardMBTI(@Param("mbti") String mbti);

    // role 목록
    @Query("SELECT DISTINCT m.card_student_role FROM Member m WHERE m.card_student_role IS NOT NULL")
    List<String> findDistinctCardRole();

    // role 필터링
    @Query("SELECT m FROM Member m WHERE m.card_student_role = :role")
    List<Member> findByCardRole (@Param("role") String role);

    // major 목록
    @Query("SELECT DISTINCT m.card_student_major FROM Member m WHERE m.card_student_major IS NOT NULL")
    List<String> findDistinctCardMajor();

    // major 필터링
    @Query("SELECT m FROM Member m WHERE m.card_student_major = :major")
    List<Member> findByCardMajor(@Param("major") String major);

}
