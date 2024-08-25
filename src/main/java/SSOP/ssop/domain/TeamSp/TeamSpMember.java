package SSOP.ssop.domain.TeamSp;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Card;
import jakarta.persistence.*;

@Entity
public class TeamSpMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamSp teamSp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


    // 기본 생성자
    public TeamSpMember() {}

    // 전체 필드를 초기화하는 생성자
    public TeamSpMember(TeamSp teamSp, User user, Card card) {
        this.teamSp = teamSp;
        this.user = user;
        this.card = card;
    }

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    public Long getCardId() {
        return card != null ? card.getCard_id() : null;
    }

    public Long getId() {
        return id;
    }

    public TeamSp getTeamSp() {
        return teamSp;
    }

    public void setTeamSp(TeamSp teamSp) {
        this.teamSp = teamSp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
