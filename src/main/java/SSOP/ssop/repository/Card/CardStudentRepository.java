package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.CardStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardStudentRepository extends JpaRepository<CardStudent, Long> {
    CardStudent findByCard_CardId(Long cardId);
}
