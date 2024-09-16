package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.TeamSp.TeamSpMemSortedDto;
import SSOP.ssop.dto.card.response.CardSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserId(Long userId);

    @Query("SELECT cs FROM CardStudent cs WHERE cs.card.cardId = :cardId")
    CardStudent findCardStudentByCardId(@Param("cardId") Long cardId);

    @Query("SELECT cw FROM CardWorker cw WHERE cw.card.cardId = :cardId")
    CardWorker findCardWorkerByCardId(@Param("cardId") Long cardId);

    @Query("SELECT cf FROM CardFan cf WHERE cf.card.cardId = :cardId")
    CardFan findCardFanByCardId(@Param("cardId") Long cardId);

    // 특정 사용자가 수신한 카드 목록 조회
    List<Card> findAllByRecipient_UserId(Long userId);

    // 특정 사용자가 소유한 모든 카드 조회
    List<Card> findAllByUser_UserId(Long userId);

    // 특정 상태에 따른 수신자가 있는 카드 목록 조회
    List<Card> findAllByRecipient_UserIdAndStatus(Long userId, String status);

    // 카드 검색
    @Query("SELECT new SSOP.ssop.dto.card.response.CardSearchResponse(" +
            "c.cardId, c.card_name, c.card_introduction, c.card_birth, c.card_template, " +
            "c.card_cover) " +
            "FROM Card c " +
            "WHERE " +
            "LOWER(c.card_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_introduction) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_template) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_cover) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_tel) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_sns_insta) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_sns_x) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_MBTI) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_music) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_movie) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_hobby) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.card_address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.memo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CardSearchResponse> searchByKeyword(@Param("keyword") String keyword);
}
