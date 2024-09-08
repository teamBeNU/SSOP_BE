package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    @Column(nullable = false, name = "card_cover")
    private String card_cover;     // 카드 커버(free, avatar, picture)

    @Column(nullable = false, name = "profile_image_url")
    private String profile_image_url;   // 이미지

    // 공통 선택

    @Column(name = "card_birth")
    private LocalDate card_birth;   // 생년월일

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
//    @ElementCollection
//    @JoinTable(name = "teamSpMember_card_student_role", joinColumns = @JoinColumn(name = "card_id"))
//    @Column(name = "card_student_role")
//    private Set<String> card_student_role = new HashSet<>();    // 역할

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

//    // 아바타 (card_cover가 avater인 경우)(카드 수정 없으면 필요 없음)
//    private int face;   // 얼굴
//    private int hair;   // 헤어
//    private int hairColor;  // 헤어 컬러
//    private int clothes;    // 옷
//    private int acc;        // 악세사리
//    private int bg;         // 배경
//    private int bgColor;    // 배경색

    public Member() {

    }

    public Member(String card_name, String card_introduction, String card_cover, String profile_image_url, LocalDate card_birth, String card_MBTI, String card_tel, String card_email, String card_insta, String card_x, String card_hobby, String card_music, String card_movie, String card_address, String card_free_1, String card_free_2, String card_free_3, String card_free_4, String card_free_5, String card_student_school, String card_student_grade, String card_student_major, String card_student_id, String card_student_club, String card_student_role, String card_student_status, String card_worker_company, String card_worker_job, String card_worker_position, String card_worker_department, String card_fan_genre, String card_fan_first, String card_fan_second, String card_fan_reason) {
        this.card_name = card_name;
        this.card_introduction = card_introduction;
        this.card_cover = card_cover;
        this.profile_image_url = profile_image_url;
        this.card_birth = card_birth;
        this.card_MBTI = card_MBTI;
        this.card_tel = card_tel;
        this.card_email = card_email;
        this.card_insta = card_insta;
        this.card_x = card_x;
        this.card_hobby = card_hobby;
        this.card_music = card_music;
        this.card_movie = card_movie;
        this.card_address = card_address;
        this.card_free_1 = card_free_1;
        this.card_free_2 = card_free_2;
        this.card_free_3 = card_free_3;
        this.card_free_4 = card_free_4;
        this.card_free_5 = card_free_5;
        this.card_student_school = card_student_school;
        this.card_student_grade = card_student_grade;
        this.card_student_major = card_student_major;
        this.card_student_id = card_student_id;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
        this.card_student_status = card_student_status;
        this.card_worker_company = card_worker_company;
        this.card_worker_job = card_worker_job;
        this.card_worker_position = card_worker_position;
        this.card_worker_department = card_worker_department;
        this.card_fan_genre = card_fan_genre;
        this.card_fan_first = card_fan_first;
        this.card_fan_second = card_fan_second;
        this.card_fan_reason = card_fan_reason;
    }

    public Long getCardId() {
        return cardId;
    }

    public TeamSpMember getTeamSpMember() {
        return teamSpMember;
    }

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_insta() {
        return card_insta;
    }

    public String getCard_x() {
        return card_x;
    }

    public String getCard_hobby() {
        return card_hobby;
    }

    public String getCard_music() {
        return card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public String getCard_address() {
        return card_address;
    }

    public String getCard_free_1() {
        return card_free_1;
    }

    public String getCard_free_2() {
        return card_free_2;
    }

    public String getCard_free_3() {
        return card_free_3;
    }

    public String getCard_free_4() {
        return card_free_4;
    }

    public String getCard_free_5() {
        return card_free_5;
    }

    public String getCard_student_school() {
        return card_student_school;
    }

    public String getCard_student_grade() {
        return card_student_grade;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_id() {
        return card_student_id;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public String getCard_student_status() {
        return card_student_status;
    }

    public String getCard_worker_company() {
        return card_worker_company;
    }

    public String getCard_worker_job() {
        return card_worker_job;
    }

    public String getCard_worker_position() {
        return card_worker_position;
    }

    public String getCard_worker_department() {
        return card_worker_department;
    }

    public String getCard_fan_genre() {
        return card_fan_genre;
    }

    public String getCard_fan_first() {
        return card_fan_first;
    }

    public String getCard_fan_second() {
        return card_fan_second;
    }

    public String getCard_fan_reason() {
        return card_fan_reason;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public void setTeamSpMember(TeamSpMember teamSpMember) {
        this.teamSpMember = teamSpMember;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public void setCard_introduction(String card_introduction) {
        this.card_introduction = card_introduction;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public void setCard_birth(LocalDate card_birth) {
        this.card_birth = card_birth;
    }

    public void setCard_MBTI(String card_MBTI) {
        this.card_MBTI = card_MBTI;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public void setCard_email(String card_email) {
        this.card_email = card_email;
    }

    public void setCard_insta(String card_insta) {
        this.card_insta = card_insta;
    }

    public void setCard_x(String card_x) {
        this.card_x = card_x;
    }

    public void setCard_hobby(String card_hobby) {
        this.card_hobby = card_hobby;
    }

    public void setCard_music(String card_music) {
        this.card_music = card_music;
    }

    public void setCard_movie(String card_movie) {
        this.card_movie = card_movie;
    }

    public void setCard_address(String card_address) {
        this.card_address = card_address;
    }

    public void setCard_free_1(String card_free_1) {
        this.card_free_1 = card_free_1;
    }

    public void setCard_free_2(String card_free_2) {
        this.card_free_2 = card_free_2;
    }

    public void setCard_free_3(String card_free_3) {
        this.card_free_3 = card_free_3;
    }

    public void setCard_free_4(String card_free_4) {
        this.card_free_4 = card_free_4;
    }

    public void setCard_free_5(String card_free_5) {
        this.card_free_5 = card_free_5;
    }

    public void setCard_student_school(String card_student_school) {
        this.card_student_school = card_student_school;
    }

    public void setCard_student_grade(String card_student_grade) {
        this.card_student_grade = card_student_grade;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }

    public void setCard_student_id(String card_student_id) {
        this.card_student_id = card_student_id;
    }

    public void setCard_student_club(String card_student_club) {
        this.card_student_club = card_student_club;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }

    public void setCard_student_status(String card_student_status) {
        this.card_student_status = card_student_status;
    }

    public void setCard_worker_company(String card_worker_company) {
        this.card_worker_company = card_worker_company;
    }

    public void setCard_worker_job(String card_worker_job) {
        this.card_worker_job = card_worker_job;
    }

    public void setCard_worker_position(String card_worker_position) {
        this.card_worker_position = card_worker_position;
    }

    public void setCard_worker_department(String card_worker_department) {
        this.card_worker_department = card_worker_department;
    }

    public void setCard_fan_genre(String card_fan_genre) {
        this.card_fan_genre = card_fan_genre;
    }

    public void setCard_fan_first(String card_fan_first) {
        this.card_fan_first = card_fan_first;
    }

    public void setCard_fan_second(String card_fan_second) {
        this.card_fan_second = card_fan_second;
    }

    public void setCard_fan_reason(String card_fan_reason) {
        this.card_fan_reason = card_fan_reason;
    }
}