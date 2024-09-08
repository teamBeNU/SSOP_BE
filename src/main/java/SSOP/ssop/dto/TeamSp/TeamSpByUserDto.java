package SSOP.ssop.dto.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpByUserDto {
    private Long teamId;
    private String team_name;
    private String team_comment;
    private Long cardId;
    private List<MemberResponse> members;

    public TeamSpByUserDto() {}

    public TeamSpByUserDto(Long teamId, String team_name, String team_comment, Long cardId, List<MemberResponse> members) {
        this.teamId = teamId;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.cardId = cardId;
        this.members = members;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
