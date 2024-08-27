package SSOP.ssop.repository;

import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardWorkerRepository extends CrudRepository<CardWorker, Long> {
    //CardWorker findByCardId(Long cardId);
   CardWorker findByCard_CardId(Long cardId);
}
