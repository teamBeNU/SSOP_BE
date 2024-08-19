package SSOP.ssop.domain.card;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "card")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Card {

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long card_id;

    @Column(nullable = false)
    private Long userId;

    // 공통 필수
    @Column(nullable = false, length = 20)
    private String card_name;

    @Column(nullable = false)
    private String card_introduction;

    @Column(nullable = false)
    private String card_template;

    @Column(nullable = false)
    private String card_cover;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Avatar avatar;  // card_cover가 avatar일 때만 사용

    @Column(nullable = false)
    private String profile_image_url;

    // 공통 선택
    @Embedded
    private SNS card_SNS;

    private String card_email;

    private String card_MBTI;

    @Embedded
    private Music card_music;

    private String card_movie;

    protected Card() {}

    @Builder
    public Card(
            String card_name,
            String card_introduction,
            String card_template,
            String card_cover,
            Avatar avatar,
            String profile_image_url,
            SNS card_SNS,
            String card_email,
            String card_MBTI,
            Music card_music,
            String card_movie
    ) {
        if (card_name == null || card_name.isBlank() ||
                card_introduction == null || card_introduction.isBlank() ||
                card_template == null || card_cover == null || profile_image_url == null) {
            throw new IllegalArgumentException();
        }

        this.card_name = card_name;
        this.card_introduction = card_introduction;
        this.card_template = card_template;
        this.card_cover = card_cover;
        this.avatar = avatar;
        this.profile_image_url = profile_image_url;
        this.card_SNS = card_SNS;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
        this.card_movie = card_movie;
    }

//    // card_essential
//    @Column(nullable = false)
//    private String card_name;
//
//    @Column(nullable = false)
//    private String card_introduction;
//
//    // card_optional
//    private String card_SNS;
//    private String card_email;
//    private String card_MBTI;
//    private String card_music;
//
//    // template_essential
//    @Column(nullable = false)
//    private String card_tel;
//
//    @Column(nullable = false)
//    private String card_birth;
//
//    @Column(nullable = false)
//    private String card_school;
//
//    @Column(nullable = false)
//    private int card_grade;
//
//    // template_optional
//    private Integer card_studentId;
//    private String card_student_major;
//    private String card_student_role;
//    private String card_student_club;

    @Lob
    private String memo;

//    protected Card() {
//    }
//
//    public Card(User user, Long card_id, String card_template, String card_cover, String card_name, String card_introduction, String card_tel, String card_birth, String card_school, int card_grade, String memo) {
//        if( card_template == null || card_cover == null || card_name == null || card_introduction == null || card_tel == null || card_birth == null || card_school == null) {
//            throw new IllegalArgumentException();
//        }
//        this.card_template = card_template;
//        this.card_cover = card_cover;
//        this.card_name = card_name;
//        this.card_introduction = card_introduction;
//        this.card_tel = card_tel;
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        this.card_birth = card_birth;
//
//        this.card_school = card_school;
//        this.card_grade = card_grade;
//
//        this.memo = memo;
//    }

/*
    public void updateCard(String card_template, String card_cover, String card_name, String card_introduction, String card_SNS, String card_email, String card_MBTI, String card_music, String card_tel, String card_birth, String card_school, Integer card_grade, Integer card_studentId, String card_student_major, String card_student_role, String card_student_club) {
        if(card_template != null){
            this.card_template = card_template;
        }
        if(card_cover != null){
            this.card_cover = card_cover;
        }
        if(card_name != null){
            this.card_name = card_name;
        }
        if(card_introduction != null){
            this.card_introduction = card_introduction;
        }
        if(card_SNS != null){
            this.card_SNS = card_SNS;
        }
        if(card_email != null){
            this.card_email = card_email;
        }
        if(card_MBTI != null){
            this.card_MBTI = card_MBTI;
        }
        if(card_music != null){
            this.card_music = card_music;
        }
        if(card_tel != null){
            this.card_tel = card_tel;
        }
        if(card_birth != null){
            this.card_birth = card_birth;
        }
        if(card_school != null){
            this.card_school = card_school;
        }
        if(card_grade != null){
            this.card_grade = card_grade;
        }
        if(card_studentId != null){
            this.card_studentId = card_studentId;
        }
        if(card_student_major != null){
            this.card_student_major = card_student_major;
        }
        if(card_student_role != null){
            this.card_student_role = card_student_role;
        }
        if(card_student_club != null){
            this.card_student_club = card_student_club;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    public Long getCard_id() {
        return card_id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public String getCard_template() {
        return card_template;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public SNS getCard_SNS() {
        return card_SNS;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public Music getCard_music() {
        return card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public void setCard_introduction(String card_introduction) {
        this.card_introduction = card_introduction;
    }

    public void setCard_template(String card_template) {
        this.card_template = card_template;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        if (avatar != null) {
            avatar.setCard(this);
        }
    }



    /*
//    public String getCard_SNS() {
//        return card_SNS;
//    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public String getCard_music() {
        return card_music;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public String getCard_birth() {
        return card_birth;
    }

    public String getCard_school() {
        return card_school;
    }

    public Integer getCard_grade() {
        return card_grade;
    }

    public Integer getCard_studentId() {
        return card_studentId;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }*/
}
