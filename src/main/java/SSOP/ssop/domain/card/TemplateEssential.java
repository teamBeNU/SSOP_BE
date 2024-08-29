package SSOP.ssop.domain.card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateEssential {
    private String card_tel;
    private String card_birth;
    private String card_school;
    private Integer card_grade;

    public TemplateEssential(String card_tel, String card_birth, String card_school, Integer card_grade) {
        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_school = card_school;
        this.card_grade = card_grade;
    }
}
