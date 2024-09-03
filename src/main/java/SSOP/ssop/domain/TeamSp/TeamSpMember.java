package SSOP.ssop.domain.TeamSp;

import SSOP.ssop.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "card_id")
    private Long cardId;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "teamSpMember", cascade = CascadeType.ALL, orphanRemoval = true)  // 이 라인을 추가
    private List<Member> members = new ArrayList<>();

    // 기본 생성자
    public TeamSpMember() {}

    // 전체 필드를 초기화하는 생성자
    public TeamSpMember(TeamSp teamSp, User user, Long cardId) {
        this.teamSp = teamSp;
        this.user = user;
        this.cardId = cardId;
    }

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
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

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
