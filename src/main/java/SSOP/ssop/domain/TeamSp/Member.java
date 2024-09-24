package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardId")
    private Long cardId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamsp_member_id") // TeamSpMember의 id를 참조 (team_id와 user_id를 가진)
    private TeamSpMember teamSpMember;

    // 공통 필수
    @Column(nullable = false, name = "card_name")
    private String card_name;   // 이름

    @Column(nullable = false, name = "card_introduction")
    private String card_introduction;   // 한줄소개

    @Column(nullable = false, name = "card_template")
    private String card_template;   // 템플릿 상태

    @Column(nullable = false, name = "card_cover")
    private String card_cover;     // 카드 커버(free, avatar, picture)

    @Column(nullable = false, name = "profile_image_url")
    private String profile_image_url;   // 이미지

    // 공통 선택
    @Column(name = "card_birth")
    private String card_birth;   // 생년월일

    @Column(name = "card_bSecret")
    private Boolean card_bSecret;      // 생년월일 비밀

    @Column(name = "card_MBTI")
    private String card_MBTI;     // mbti

    @Column(name = "card_tel")
    private String card_tel;     // 연락처

    @Column(name = "card_email")
    private String card_email;     // 이메일

    @Column(name = "card_insta")
    private String card_insta;     // 인스타

    @Column(name = "card_x")
    private String card_x;     // x(트위터)

    @Column(name = "card_hobby")
    private String card_hobby;     // 취미

    @Column(name = "card_music")
    private String card_music;      // 인생음악

    @Column(name = "card_movie")
    private String card_movie;     // 인생영화

    @Column(name = "card_address")
    private String card_address;     // 거주지

    // 자유 (학생, 직장인, 팬 모두 가지고 있는 공통 선택)
    @Column(name = "card_free_A1")
    private String card_free_1;

    @Column(name = "card_free_A2")
    private String card_free_2;

    @Column(name = "card_free_A3")
    private String card_free_3;

    @Column(name = "card_free_A4")
    private String card_free_4;

    @Column(name = "card_free_A5")
    private String card_free_5;

    // 학생
    @Column(name = "card_student_school")
    private String card_student_school;     // 학교

    @Column(name = "card_student_grade")
    private String card_student_grade;     // 학년

    @Column(name = "card_student_major")
    private String card_student_major;     // 전공

    @Column(name = "card_student_id")
    private String card_student_id;         // 학번

    @Column(name = "card_student_club")
    private String card_student_club;     // 동아리

    @Column(name = "card_student_role")
    private String card_student_role;    // 역할

    @Column(name = "card_student_status")
    private String card_student_status;  // 재학상태

    // 직장인
    @Column(name = "card_worker_company")
    private String card_worker_company;     // 회사

    @Column(name = "card_worker_job")
    private String card_worker_job;         // 직무

    @Column(name = "card_worker_position")
    private String card_worker_position;    // 직위

    @Column(name = "card_worker_department")
    private String card_worker_department;  // 부서

    // 팬
    @Column(name = "card_fan_genre")
    private String card_fan_genre;      // 덕질장르

    @Column(name = "card_fan_first")
    private String card_fan_first;      // 최애 차애 입덕계기

    @Column(name = "card_fan_second")
    private String card_fan_second;     // 차애

    @Column(name = "card_fan_reason")
    private String card_fan_reason;     // 입덕계기

}