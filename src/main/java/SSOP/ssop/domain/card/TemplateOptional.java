package SSOP.ssop.domain.card;

public class TemplateOptional {
    private Integer card_studentId;
    private String card_student_major;
    private String card_student_role;
    private String card_student_club;

    public TemplateOptional(Integer card_studentId, String card_student_major, String card_student_role, String card_student_club) {
        this.card_studentId = card_studentId;
        this.card_student_major = card_student_major;
        this.card_student_role = card_student_role;
        this.card_student_club = card_student_club;
    }

    public Integer getCard_studentId() {
        return card_studentId;
    }

    public void setCard_studentId(Integer card_studentId) {
        this.card_studentId = card_studentId;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public void setCard_student_club(String card_student_club) {
        this.card_student_club = card_student_club;
    }
}
