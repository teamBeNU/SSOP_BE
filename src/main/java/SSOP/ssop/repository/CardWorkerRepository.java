package SSOP.ssop.repository;

import SSOP.ssop.domain.card.CardWorker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardWorkerRepository extends CrudRepository<CardWorker, Long> {
}
