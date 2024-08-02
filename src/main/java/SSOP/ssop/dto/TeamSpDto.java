package SSOP.ssop.dto;

import SSOP.ssop.domain.TeamSp;
import lombok.Data;
import java.util.ArrayList;
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

    // 초기화 생성자
    public TeamSpDto(String team_name, String team_comment, Boolean isTemplate, String template, Boolean showAge, Boolean showSchool, Boolean showGrade, Boolean showStudNum, Boolean showMajor, List<String> showRole, Boolean showClub, Boolean showTel, Boolean showSNS, Boolean showEmail, Boolean showMBTI, Boolean showMusic, Boolean showMovie) {
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.isTemplate = isTemplate;
        this.template = template;

        this.showAge = showAge;
        this.showSchool = showSchool;
        this.showGrade = showGrade;
        this.showStudNum = showStudNum;
        this.showMajor = showMajor;
        this.showRole = showRole;
        this.showClub = showClub;
        this.showTel = showTel;
        this.showSNS = showSNS;
        this.showEmail = showEmail;
        this.showMBTI = showMBTI;
        this.showMusic = showMusic;
        this.showMovie = showMovie;
    }

    // TeamSp 객체를 DTO로 변환하는 생성자
    public TeamSpDto(TeamSp teamSp) {
        this.team_name = teamSp.getTeam_name();
        this.team_comment = teamSp.getTeam_comment();
        this.isTemplate = teamSp.getIsTemplate();
        this.template = teamSp.getTemplate();

        this.showAge = teamSp.getShowAge();
        this.showSchool = teamSp.getShowSchool();
        this.showGrade = teamSp.getShowGrade();
        this.showStudNum = teamSp.getShowStudNum();
        this.showMajor = teamSp.getShowMajor();
        this.showRole = teamSp.getShowRole();
        this.showClub = teamSp.getShowClub();
        this.showTel = teamSp.getShowTel();
        this.showSNS = teamSp.getShowSNS();
        this.showEmail = teamSp.getShowEmail();
        this.showMBTI = teamSp.getShowMBTI();
        this.showMusic = teamSp.getShowMusic();
        this.showMovie = teamSp.getShowMovie();
    }
}
