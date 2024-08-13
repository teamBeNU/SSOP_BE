package SSOP.ssop.domain.TeamSp;

import SSOP.ssop.domain.User;
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

    // 기본 생성자
    public TeamSpMember() {}

    // 전체 필드를 초기화하는 생성자
    public TeamSpMember(TeamSp teamSp, User user) {
        this.teamSp = teamSp;
        this.user = user;
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
}
