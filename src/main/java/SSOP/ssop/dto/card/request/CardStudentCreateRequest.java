package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardStudentCreateRequest {

    private String card_student_school;     // 학교
    private String card_student_grade;      // 학년
    private String card_student_major;      // 전공
    private String card_student_id;         // 학번
    private String card_student_club;       // 동아리
    private String card_student_role;       // 역할
    private String card_student_status;     // 재학상태
   }
