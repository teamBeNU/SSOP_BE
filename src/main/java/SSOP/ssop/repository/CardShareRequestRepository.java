package SSOP.ssop.repository;

import SSOP.ssop.domain.bluetooth.CardShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardShareRequestRepository extends JpaRepository<CardShareRequest, Long> {
    List<CardShareRequest> findByRecipientId(Long recipientId);
}
