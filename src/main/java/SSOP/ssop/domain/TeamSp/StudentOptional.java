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

    public boolean checkNullValue() {
        return showSchool == null && showGrade == null && showStudNum == null && showMajor == null &&
                showClub == null && (showRole == null || showRole.isEmpty()) && showStatus == null;
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
}