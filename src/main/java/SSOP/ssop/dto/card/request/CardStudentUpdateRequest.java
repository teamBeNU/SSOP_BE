package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardStudentUpdateRequest {
    // 필수
    private String card_student_school;     // 학교
    private String card_student_grade;      // 학년(초중고-1~6학년, 대학(원)-1~4학년+추가학기)
    private String card_student_major;  // 전공(대학(원)생만 필수)

    // 선택
    private String card_student_id;         // 학번
    private String card_student_club;       // 동아리
    private String card_student_role;       // 역할
    private String card_student_status;  // 재학상태(재학 중, 휴학 중, 졸업 예정)

   }
