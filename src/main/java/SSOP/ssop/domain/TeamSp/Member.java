package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private long card_id;

    @Column(name = "card_name")
    private String card_name;

    @Column(name = "card_birth")
    private String card_birth;

    @Column(name = "role")
    private String role;

    @Column(name = "major")
    private String major;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "team_id") // team_id는 TeamSpMember의 team_id를 참조
    private TeamSpMember teamSpMember;
}