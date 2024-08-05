package SSOP.ssop.repository;

import SSOP.ssop.domain.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUser_UserId(Long userId);
}