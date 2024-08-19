package SSOP.ssop.repository;

import SSOP.ssop.domain.bluetooth.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    List<Recipient> findAll();
}
