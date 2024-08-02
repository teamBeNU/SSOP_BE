package SSOP.ssop.repository;

import SSOP.ssop.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

   // Optional<Card> findByCardId(@Param("card_id"));
}
