package SSOP.ssop.repository.Card;

import SSOP.ssop.domain.card.CardWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardWorkerRepository extends JpaRepository<CardWorker, Long> {
   CardWorker findByCard_CardId(Long cardId);
}
