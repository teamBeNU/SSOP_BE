package SSOP.ssop.dto.TeamSp;

public class MemberStudentDto {
    private String card_student_school;     // 학교
    private String card_student_grade;     // 학년
    private String card_student_id;         // 학번
    private String card_student_major;     // 전공
    private String card_student_club;     // 동아리
    private String card_student_role;     // 역할
    private String card_student_status;     // 재학상태

    public MemberStudentDto(String card_student_school, String card_student_grade, String card_student_id, String card_student_major, String card_student_club, String card_student_role, String card_student_status) {
        this.card_student_school = card_student_school;
        this.card_student_grade = card_student_grade;
        this.card_student_id = card_student_id;
        this.card_student_major = card_student_major;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
        this.card_student_status = card_student_status;
    }

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

    public String getCard_student_status() { return card_student_status; }
}
