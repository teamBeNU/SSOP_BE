package SSOP.ssop.domain.card;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "card")
@Inheritance(strategy = InheritanceType.JOINED)       // @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")     // @DiscriminatorColumn(name = "TemplateType")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long card_id = null;

    @Column(nullable = false)
    private Long user_id;

    // 공통 필수
    @Column(nullable = false, length = 20)
    private String card_name;

    @Column(nullable = false)
    private String card_introduction;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "template")
//    private CardTemplate template;

    @Column(nullable = false)
    private String template;

    @Column(nullable = false)
    private String card_cover;

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
    public Card(String card_name, String card_introduction, String template, String card_cover, SNS card_SNS, String card_email, String card_MBTI, Music card_music, String card_movie) {
        if (card_name == null || card_name.isBlank() ||
                card_introduction == null || card_introduction.isBlank() ||
                template == null || card_cover == null) {
            throw new IllegalArgumentException();
        }

        this.card_name = card_name;
        this.card_introduction = card_introduction;
        this.template = template;
        this.card_cover = card_cover;
        this.card_SNS = card_SNS;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
        this.card_movie = card_movie;
    }

    public Long getCard_id() {
        return card_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public String getTemplate() {
        return template;
    }

    public String getCard_cover() {
        return card_cover;
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

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public void setCard_introduction(String card_introduction) {
        this.card_introduction = card_introduction;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public void setCard_SNS(SNS card_SNS) {
        this.card_SNS = card_SNS;
    }

    public void setCard_email(String card_email) {
        this.card_email = card_email;
    }

    public void setCard_MBTI(String card_MBTI) {
        this.card_MBTI = card_MBTI;
    }

    public void setCard_music(Music card_music) {
        this.card_music = card_music;
    }

    public void setCard_movie(String card_movie) {
        this.card_movie = card_movie;
    }
}
