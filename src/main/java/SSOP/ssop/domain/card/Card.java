package SSOP.ssop.domain.card;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "card")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Card {

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private Long userId;

    // 공통 필수
    @Column(nullable = false, length = 20)
    private String card_name;       // 이름

    @Column(nullable = false)
    private String card_introduction;       // 한줄소개

    @Column(nullable = false)
    private String card_template;       // 템플릿 종류(student, worker, fan, free)

    @Column(nullable = false)
    private String card_cover;     // 카드 커버(avatar, picture)

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Avatar avatar;  // card_cover가 avatar일 때만 사용

    @Column(nullable = false)
    private String profile_image_url;   // 이미지

    // 공통 선택
    private String card_birth;      // 생년월일

    private Boolean card_bSecrete;      // 생년월일 비밀

    private String card_tel;    // 연락처

    private String card_sns_insta;  // sns: insta

    private String card_sns_x;      // sns: x

    private String card_email;      // 이메일

    private String card_MBTI;       // mbti

    private String card_music;      // 음악

    private String card_movie;      // 영화

    private String card_hobby;      // 취미

    private String card_address;        // 거주지



    @Lob
    private String memo;

    public Card() {}

    @Builder
    public Card(String card_name, String card_introduction, String card_template, String card_cover, Avatar avatar, String profile_image_url, String card_birth, Boolean card_bSecrete, String card_tel, String card_sns_insta, String card_sns_x, String card_email, String card_MBTI, String card_music, String card_movie, String card_hobby, String card_address) {
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
        this.card_birth = card_birth;
        this.card_bSecrete = card_bSecrete;
        this.card_tel = card_tel;
        this.card_sns_insta = card_sns_insta;
        this.card_sns_x = card_sns_x;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
        this.card_movie = card_movie;
        this.card_hobby = card_hobby;
        this.card_address = card_address;
    }

    public Long getCardId() {
        return cardId;
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

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getCard_birth() {
        return card_birth;
    }

    public Boolean getCard_bSecrete() {
        return card_bSecrete;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public String getCard_sns_insta() {
        return card_sns_insta;
    }

    public String getCard_sns_x() {
        return card_sns_x;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public String getCard_music() {
        return card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public String getCard_hobby() {
        return card_hobby;
    }

    public String getCard_address() {
        return card_address;
    }

    public String getMemo() {
        return memo;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public void setCard_birth(String card_birth) {
        this.card_birth = card_birth;
    }

    public void setCard_bSecrete(Boolean card_bSecrete) {
        this.card_bSecrete = card_bSecrete;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public void setCard_sns_insta(String card_sns_insta) {
        this.card_sns_insta = card_sns_insta;
    }

    public void setCard_sns_x(String card_sns_x) {
        this.card_sns_x = card_sns_x;
    }

    public void setCard_email(String card_email) {
        this.card_email = card_email;
    }

    public void setCard_MBTI(String card_MBTI) {
        this.card_MBTI = card_MBTI;
    }

    public void setCard_music(String card_music) {
        this.card_music = card_music;
    }

    public void setCard_movie(String card_movie) {
        this.card_movie = card_movie;
    }

    public void setCard_hobby(String card_hobby) {
        this.card_hobby = card_hobby;
    }

    public void setCard_address(String card_address) {
        this.card_address = card_address;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
