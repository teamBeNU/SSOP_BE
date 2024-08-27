package SSOP.ssop.repository;

import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardFanRepository extends JpaRepository<CardFan, Integer> {
    //CardFan findByCardId(Long cardId);
    CardFan findByCard_CardId(Long cardId);
}
