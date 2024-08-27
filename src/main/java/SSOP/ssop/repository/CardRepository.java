package SSOP.ssop.repository;

import SSOP.ssop.domain.card.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUserId(Long userId);

//    @EntityGraph(attributePaths = {"avatar", "cardStudent", "cardWorker", "cardFan"})
//    List<Card> findAll();
}
