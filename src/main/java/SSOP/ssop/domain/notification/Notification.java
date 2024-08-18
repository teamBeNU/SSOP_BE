package SSOP.ssop.domain.notification;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String card_name;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Notification(Long id, String title, String card_name, boolean accepted, User user) {
        this.id = id;
        this.title = title;
        this.card_name = card_name;
        this.accepted = accepted;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCard_name() {
        return card_name;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
