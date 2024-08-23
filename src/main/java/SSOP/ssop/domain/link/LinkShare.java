package SSOP.ssop.domain.link;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LinkShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long card_id;
    private String link;
    private LocalDateTime expiryTime;

    // 기본 생성자
    public LinkShare() {}

    // 생성자
    public LinkShare(Long card_id, String link, LocalDateTime expiryTime) {
        this.card_id = card_id;
        this.link = link;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
