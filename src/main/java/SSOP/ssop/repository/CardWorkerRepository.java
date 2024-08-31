package SSOP.ssop.repository;

import SSOP.ssop.domain.card.CardWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardWorkerRepository extends JpaRepository<CardWorker, Long> {
    //CardWorker findByCardId(Long cardId);
   CardWorker findByCard_CardId(Long cardId);
}
