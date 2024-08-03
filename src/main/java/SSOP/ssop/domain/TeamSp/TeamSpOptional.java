package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamSpOptional {
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

