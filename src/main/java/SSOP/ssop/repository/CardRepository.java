package SSOP.ssop.repository;

import SSOP.ssop.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findCardsById(Long id);
}
