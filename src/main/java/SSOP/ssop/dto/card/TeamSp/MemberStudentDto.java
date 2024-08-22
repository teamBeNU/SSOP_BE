package SSOP.ssop.dto.card.TeamSp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStudentDto {
    private String card_student_school;     // 학교
    private String card_student_grade;     // 학년
    private String card_student_id;         // 학번
    private String card_student_major;     // 전공
    private String card_student_club;     // 동아리
    private String card_student_role;     // 역할

    public String getCard_student_school() {
        return card_student_school;
    }

    public String getCard_student_grade() {
        return card_student_grade;
    }

    public String getCard_student_id() {
        return card_student_id;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public String getCard_student_role() {
        return card_student_role;
    }
}
