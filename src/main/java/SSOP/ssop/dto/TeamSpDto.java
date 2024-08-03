package SSOP.ssop.dto;

import SSOP.ssop.domain.TeamSp;
import lombok.Data;
import java.util.List;

@Data
public class TeamSpDto {

    private Long team_id;
    private String team_name;
    private String team_comment;
    private Boolean isTemplate;
    private String template;

    private Boolean showAge;
    private Boolean showSchool;
    private Boolean showGrade;
    private Boolean showStudNum;
    private Boolean showMajor;
    private List<String> showRole;
    private Boolean showClub;
    private Boolean showTel;
    private Boolean showSNS;
    private Boolean showEmail;
    private Boolean showMBTI;
    private Boolean showMusic;
    private Boolean showMovie;
}
