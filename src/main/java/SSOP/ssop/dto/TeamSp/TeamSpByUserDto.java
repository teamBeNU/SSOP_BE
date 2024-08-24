package SSOP.ssop.dto.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpByUserDto {
    private Long team_id;
    private String team_name;
    private String team_comment;
    private Long card_id;
    private List<MemberResponse> members;

    public TeamSpByUserDto() {}

    public TeamSpByUserDto(Long team_id, String team_name, String team_comment, Long card_id, List<MemberResponse> members) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.card_id = card_id;
        this.members = members;
    }

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
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

    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
