package SSOP.ssop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamSp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long team_id;

    @Column(name = "team_name", nullable = false)
    private String team_name;

    @Column(name = "team_comment", nullable = false)
    private String team_comment;

    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @Column(name = "template")
    private String template;

    @Column(name = "inviteCode")
    private int inviteCode;

    @Column(name = "showAge")
    private Boolean showAge;

    @Column(name = "showSchool")
    private Boolean showSchool;

    @Column(name = "showGrade")
    private Boolean showGrade;

    @Column(name = "showStudNum")
    private Boolean showStudNum;

    @Column(name = "showMajor")
    private Boolean showMajor;

    @ElementCollection
    @CollectionTable(name = "team_roles", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "showRole")
    private List<String> showRole;

    @Column(name = "showClub")
    private Boolean showClub;

    @Column(name = "showTel")
    private Boolean showTel;

    @Column(name = "showSNS")
    private Boolean showSNS;

    @Column(name = "showEmail")
    private Boolean showEmail;

    @Column(name = "showMBTI")
    private Boolean showMBTI;

    @Column(name = "showMusic")
    private Boolean showMusic;

    @Column(name = "showMovie")
    private Boolean showMovie;

    public void updateTeamName(String team_name) {
        this.team_name = team_name;
    }
}
