package SSOP.ssop.repository;

import SSOP.ssop.domain.card.CardStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardStudentRepository extends JpaRepository<CardStudent, Long> {

}
