package SSOP.ssop.domain.card;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("fan")
public class CardFan extends Card {

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    // 필수
    @Column(name = "card_fan_genre")
    private String card_fan_genre;      // 덕질장르

    @Column(name = "card_fan_first")
    private String card_fan_first;      // 최애 차애 입덕계기

    @Column(name = "card_fan_second")
    private String card_fan_second;     // 차애

    @Column(name = "card_fan_reason")
    private String card_fan_reason;     // 입덕계기

    public CardFan() {
        super();
    }

    public CardFan(
            String card_name,
            String card_introduction,
            String card_template,
            String card_cover,
            Avatar avatar,
            String profile_image_url,
            String card_birth,
            Boolean card_bSecrete,
            String card_tel,
            String card_sns_insta,
            String card_sns_x,
            String card_email,
            String card_MBTI,
            String card_music,
            String card_movie,
            String card_hobby,
            String card_address,
            String card_fan_genre,
            String card_fan_first,
            String card_fan_second,
            String card_fan_reason
    ) {
        super(card_name, card_introduction, card_template, card_cover, avatar, profile_image_url, card_birth, card_bSecrete, card_tel, card_sns_insta, card_sns_x, card_email, card_MBTI, card_music, card_movie, card_hobby, card_address);

        this.card_fan_genre = card_fan_genre;
        this.card_fan_first = card_fan_first;
        this.card_fan_second = card_fan_second;
        this.card_fan_reason = card_fan_reason;
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
