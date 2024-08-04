package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TeamSpMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private long team_id;

    @Column(name = "team_name")
    private String team_name;

    @Column(name = "team_comment")
    private String team_comment;

    @Column(name = "invite_code")
    private int inviteCode;

    @OneToOne
    @JoinColumn(name = "team_id")
    private TeamSp teamSp; // 연관 관계 필드

    @Embedded
    private Filter filter;

    @OneToMany(mappedBy = "teamSpMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    // 기본 생성자
    public TeamSpMember() {}

    // 전체 필드를 초기화하는 생성자
    public TeamSpMember(String team_name, String team_comment, int inviteCode, Filter filter) {
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.inviteCode = inviteCode;
        this.filter = filter;
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

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public TeamSp getTeamSp() {
        return teamSp;
    }

    public void setTeamSp(TeamSp teamSp) {
        this.teamSp = teamSp;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}