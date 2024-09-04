package SSOP.ssop.domain.link;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LinkShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long card_id;
    private String link;
    private LocalDateTime expiryTime;

    public LinkShare() {}

    // 생성자
    public LinkShare(Long card_id, String link, LocalDateTime expiryTime) {
        this.card_id = card_id;
        this.link = link;
        this.expiryTime = expiryTime;
    }
}
