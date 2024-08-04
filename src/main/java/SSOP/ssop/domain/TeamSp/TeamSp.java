package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
public class TeamSp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private long team_id;

    @Column(name = "team_name")
    private String team_name;

    @Column(name = "team_comment")
    private String team_comment;

    @Column(name = "isTemplate")
    private Boolean isTemplate;

    @Column(name = "template")
    private String template;

    @Column(name = "inviteCode")
    private int inviteCode;

    @Embedded
    private TeamSpOptional teamSpOptional;

    @ElementCollection
    @CollectionTable(name = "team_roles", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "showRole")
    private List<String> showRole;

    @OneToOne(mappedBy = "teamSp", cascade = CascadeType.ALL)
    private TeamSpMember teamSpMember;

    // 기본 생성자
    protected TeamSp() {}

    // 전체 필드를 초기화하는 생성자
    public TeamSp(String team_name, String team_comment, Boolean isTemplate, String template,
                  TeamSpOptional teamSpOptional, List<String> showRole) {
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.isTemplate = isTemplate;
        this.template = template;
        this.teamSpOptional = teamSpOptional;
        this.showRole = showRole;
    }

    public void updateTeamName(String team_name) {
        this.team_name = team_name;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_comment() {
        return team_comment;
    }

    public void setTeam_comment(String team_comment) {
        this.team_comment = team_comment;
    }

    public Boolean getTemplate() {
        return isTemplate;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public TeamSpOptional getTeamSpOptional() {
        return teamSpOptional;
    }

    public void setTeamSpOptional(TeamSpOptional teamSpOptional) {
        this.teamSpOptional = teamSpOptional;
    }

    public List<String> getShowRole() {
        return showRole;
    }

    public void setShowRole(List<String> showRole) {
        this.showRole = showRole;
    }

    public TeamSpMember getTeamSpMember() {
        return teamSpMember;
    }

    public void setTeamSpMember(TeamSpMember teamSpMember) {
        this.teamSpMember = teamSpMember;
    }
}
