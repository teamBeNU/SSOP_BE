package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.TeamSp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamSpInfoDto {
    private Long teamId;
    private String team_name;
    private String team_comment;
    private Boolean isTemplate;
    private String template;

    public TeamSpInfoDto(TeamSp teamSp) {
        this.teamId = teamSp.getTeamId();
        this.team_name = teamSp.getTeam_name();
        this.team_comment = teamSp.getTeam_comment();
        this.isTemplate = teamSp.getIsTemplate();
        this.template = teamSp.getTemplate();
    }
}