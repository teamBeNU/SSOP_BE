package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.CardStudent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardStudentResponse extends CardStudent {
    // 필수
    private String card_student_school;     // 학교
    private String card_student_grade;      // 학년(초중고-1~6학년, 대학(원)-1~4학년+추가학기)
    private String card_student_major;  // 전공(대학(원)생만 필수)

    // 선택
    private String card_student_id;         // 학번
    private String card_student_club;       // 동아리
    private String card_student_role;       // 역할
    private String card_student_status;  // 재학상태(재학 중, 휴학 중, 졸업 예정)

    public CardStudentResponse(
           String card_student_school,
           String card_student_grade,
           String card_student_major,
           String card_student_id,
           String card_student_club,
           String card_student_role,
           String card_student_status
    ) {
        this.card_student_school = card_student_school;
        this.card_student_grade = card_student_grade;
        this.card_student_major = card_student_major;
        this.card_student_id = card_student_id;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
        this.card_student_status = card_student_status;
    }
}
