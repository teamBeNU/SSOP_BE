package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentOptional {
    @Column(name = "student_showAge")
    private Boolean showAge;

    @Column(name = "student_showSchool")
    private Boolean showSchool;

    @Column(name = "student_showGrade")
    private Boolean showGrade;

    @Column(name = "student_showStudNum")
    private Boolean showStudNum;

    @Column(name = "student_showMajor")
    private Boolean showMajor;

    @Column(name = "student_showClub")
    private Boolean showClub;

    @ElementCollection
    @CollectionTable(name = "student_roles", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "student_showRole")
    private List<String> showRole;

    @Column(name = "student_showTel")
    private Boolean showTel;

    @Column(name = "student_showSNS")
    private Boolean showSNS;

    @Column(name = "student_showEmail")
    private Boolean showEmail;

    @Column(name = "student_showMBTI")
    private Boolean showMBTI;

    @Column(name = "student_showMusic")
    private Boolean showMusic;

    @Column(name = "student_showMovie")
    private Boolean showMovie;

    public boolean checkNullValue() {
        return showAge == null && showSchool == null && showGrade == null &&
                showStudNum == null && showMajor == null && showClub == null &&
                (showRole == null || showRole.isEmpty()) && showTel == null &&
                showSNS == null && showEmail == null && showMBTI == null &&
                showMusic == null && showMovie == null;
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

    public Boolean getShowClub() {
        return showClub;
    }

    public void setShowClub(Boolean showClub) {
        this.showClub = showClub;
    }

    public List<String> getShowRole() {
        return showRole;
    }

    public void setShowRole(List<String> showRole) {
        this.showRole = showRole;
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
}

