package SSOP.ssop.domain.card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
