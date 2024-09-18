package SSOP.ssop.repository.LinkShare;

import SSOP.ssop.domain.link.LinkShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LinkShareRepository extends JpaRepository<LinkShare, Long> {
    Optional<LinkShare> findByToken(String token);

    //void deleteByExpiresAtBefore(LocalDateTime expiryTime);
}
