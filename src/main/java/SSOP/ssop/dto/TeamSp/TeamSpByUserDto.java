package SSOP.ssop.dto.TeamSp;

import java.util.List;

public class TeamSpByUserDto {
    private String team_id;
    private String team_name;
    private String team_comment;
    private List<MemberResponse> members;

    public TeamSpByUserDto() {}

    public TeamSpByUserDto(String team_id, String team_name, String team_comment, List<MemberResponse> members) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.members = members;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public List<MemberResponse> getMembers() {
        return members;
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

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
