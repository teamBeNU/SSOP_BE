package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.CardFan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardFanRepository extends JpaRepository<CardFan, Long> {
    CardFan findByCard_CardId(Long cardId);
}
