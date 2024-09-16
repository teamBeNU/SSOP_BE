package SSOP.ssop.repository.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.dto.Search.MemberSearchDto;
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
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.teamId = :teamId")
    List<Member> findByTeamId(@Param("teamId") long teamId);

    // user id가 가진 팀스페이스 멤버 카드 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.user.userId = :userId")
    Optional<Member> findByUserId(@Param("userId") long userId);

    // team id와 user id를 통해 특정 팀스페이스 멤버 카드 조회
    @Query("SELECT m FROM Member m WHERE m.teamSpMember.teamSp.teamId = :teamId AND m.teamSpMember.user.userId = :userId")
    List<Member> findByTeamIdAndUserId(@Param("teamId") long teamId, @Param("userId") long userId);

    // 호스트 지정 카드 검색
    @Query("SELECT new SSOP.ssop.dto.Search.MemberSearchDto(" +
            "m.cardId, m.card_name, m.card_introduction, m.card_birth, m.card_template, m.card_cover) " +
            "FROM Member m " +
            "WHERE m.teamSpMember.teamSp.teamId IN :teamSpIds AND (" +
            "LOWER(m.card_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_introduction) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_template) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_cover) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_tel) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_insta) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_x) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_MBTI) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_music) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_movie) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_hobby) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.card_address) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<MemberSearchDto> searchByKeywordAndTeamSpIds(
            @Param("keyword") String keyword,
            @Param("teamSpIds") List<Long> teamSpIds);
}