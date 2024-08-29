package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentOptional {

    @Column(name = "student_showAge")
    private Boolean showAge;

    @Column(name = "student_showMBTI")
    private Boolean showMBTI;

    @Column(name = "student_showTel")
    private Boolean showTel;

    @Column(name = "student_showEmail")
    private Boolean showEmail;

    @Column(name = "student_showSNS")
    private Boolean showSNS;

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

    @Column(name = "student_showStatus")
    private Boolean showStatus;

    @Column(name = "student_showHobby")
    private Boolean showHobby;

    @Column(name = "student_showMusic")
    private Boolean showMusic;

    @Column(name = "student_showMovie")
    private Boolean showMovie;

    @Column(name = "student_showLive")
    private Boolean showLive;

    @ElementCollection
    @CollectionTable(name = "student_plus", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "student_plus")
    private List<String> plus;

    @Column(name = "student_card_cover")
    private String cardCover;

    public boolean checkNullValue() {
        return showAge == null && showMBTI == null && showTel == null && showEmail == null && showSNS == null &&
                showSchool == null && showGrade == null && showStudNum == null && showMajor == null &&
                showClub == null && (showRole == null || showRole.isEmpty()) && showStatus == null &&
                showHobby == null && showMusic == null && showMovie == null && showLive == null;
    }

    public Boolean getShowAge() {
        return showAge;
    }

    public Boolean getShowMBTI() {
        return showMBTI;
    }

    public Boolean getShowTel() {
        return showTel;
    }

    public Boolean getShowEmail() {
        return showEmail;
    }

    public Boolean getShowSNS() {
        return showSNS;
    }

    public Boolean getShowSchool() {
        return showSchool;
    }

    public Boolean getShowGrade() {
        return showGrade;
    }

    public Boolean getShowStudNum() {
        return showStudNum;
    }

    public Boolean getShowMajor() {
        return showMajor;
    }

    public Boolean getShowClub() {
        return showClub;
    }

    public List<String> getShowRole() {
        return showRole;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public Boolean getShowHobby() {
        return showHobby;
    }

    public Boolean getShowMusic() {
        return showMusic;
    }

    public Boolean getShowMovie() {
        return showMovie;
    }

    public Boolean getShowLive() {
        return showLive;
    }

    public List<String> getPlus() {
        return plus;
    }

    public String getCardCover() {
        return cardCover;
    }
}