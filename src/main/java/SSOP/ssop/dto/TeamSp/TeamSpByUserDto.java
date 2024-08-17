package SSOP.ssop.dto.TeamSp;

public class TeamSpByUserDto {
    private String team_id;
    private String team_name;
    private String team_comment;

    public TeamSpByUserDto() {}

    public TeamSpByUserDto(String team_id, String team_name, String team_comment) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
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

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_comment() {
        return team_comment;
    }

    public void setTeam_comment(String team_comment) {
        this.team_comment = team_comment;
    }
}
