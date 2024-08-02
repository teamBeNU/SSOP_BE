package SSOP.ssop.dto;

import lombok.Getter;
import lombok.Setter;

public class TeamSpDto {
    private long team_id;
    private String team_name;

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
}
