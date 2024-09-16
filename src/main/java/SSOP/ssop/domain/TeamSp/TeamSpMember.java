package SSOP.ssop.domain.TeamSp;

import SSOP.ssop.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TeamSpMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private TeamSp teamSp;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "cardId")
    private Long cardId;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "teamSpMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    // 전체 필드를 초기화하는 생성자
    public TeamSpMember(TeamSp teamSp, User user, Long cardId) {
        this.teamSp = teamSp;
        this.user = user;
        this.cardId = cardId;
    }

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }
}
