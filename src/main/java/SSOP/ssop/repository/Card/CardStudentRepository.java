package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.CardStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardStudentRepository extends JpaRepository<CardStudent, Long> {
//    CardStudent findByCardId(Long cardId);
    CardStudent findByCard_CardId(Long cardId);
}
