package SSOP.ssop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
// JPA -> lombok DB 자동생성 및 연동
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamSp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long team_id;

    @Column(name = "team_name", nullable = false)
    private String team_name;

    @Column(name = "team_comment", nullable = false)
    private String team_comment;

    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @Column(name = "template")
    private String template;

    @Column(name = "inviteCode")
    private int inviteCode;

    @Column(name = "showAge")
    private Boolean showAge;

    @Column(name = "showSchool")
    private Boolean showSchool;

    @Column(name = "showGrade")
    private Boolean showGrade;

    @Column(name = "showStudNum")
    private Boolean showStudNum;

    @Column(name = "showMajor")
    private Boolean showMajor;

    @ElementCollection
    @CollectionTable(name = "team_roles", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "showRole")
    private List<String> showRole;

    @Column(name = "showClub")
    private Boolean showClub;

    @Column(name = "showTel")
    private Boolean showTel;

    @Column(name = "showSNS")
    private Boolean showSNS;

    @Column(name = "showEmail")
    private Boolean showEmail;

    @Column(name = "showMBTI")
    private Boolean showMBTI;

    @Column(name = "showMusic")
    private Boolean showMusic;

    @Column(name = "showMovie")
    private Boolean showMovie;

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

    public Boolean getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Boolean getShowAge() {
        return showAge;
    }

    public void setShowAge(Boolean showAge) {
        this.showAge = showAge;
    }

    public Boolean getShowSchool() {
        return showSchool;
    }

    public void setShowSchool(Boolean showSchool) {
        this.showSchool = showSchool;
    }

    public Boolean getShowGrade() {
        return showGrade;
    }

    public void setShowGrade(Boolean showGrade) {
        this.showGrade = showGrade;
    }

    public Boolean getShowStudNum() {
        return showStudNum;
    }

    public void setShowStudNum(Boolean showStudNum) {
        this.showStudNum = showStudNum;
    }

    public Boolean getShowMajor() {
        return showMajor;
    }

    public void setShowMajor(Boolean showMajor) {
        this.showMajor = showMajor;
    }

    public List<String> getShowRole() {
        return showRole;
    }

    public void setShowRole(List<String> showRole) {
        this.showRole = showRole;
    }

    public Boolean getShowClub() {
        return showClub;
    }

    public void setShowClub(Boolean showClub) {
        this.showClub = showClub;
    }

    public Boolean getShowTel() {
        return showTel;
    }

    public void setShowTel(Boolean showTel) {
        this.showTel = showTel;
    }

    public Boolean getShowSNS() {
        return showSNS;
    }

    public void setShowSNS(Boolean showSNS) {
        this.showSNS = showSNS;
    }

    public Boolean getShowEmail() {
        return showEmail;
    }

    public void setShowEmail(Boolean showEmail) {
        this.showEmail = showEmail;
    }

    public Boolean getShowMBTI() {
        return showMBTI;
    }

    public void setShowMBTI(Boolean showMBTI) {
        this.showMBTI = showMBTI;
    }

    public Boolean getShowMusic() {
        return showMusic;
    }

    public void setShowMusic(Boolean showMusic) {
        this.showMusic = showMusic;
    }

    public Boolean getShowMovie() {
        return showMovie;
    }

    public void setShowMovie(Boolean showMovie) {
        this.showMovie = showMovie;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }
}
