package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
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
}
