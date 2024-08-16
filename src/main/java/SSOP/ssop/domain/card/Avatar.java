package SSOP.ssop.domain.card;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "avatar")
public class Avatar {

    @Id
    @Column(name = "card_id")
    private Long card_id;

    // 다른 필드들
    private int face;   // 얼굴
    private int hair;   // 헤어
    private int hairColor;  // 헤어 컬러
    private int clothes;    // 옷
    private int acc;        // 악세사리
    private int bg;         // 배경
    private int bgColor;    // 배경색

    @OneToOne
    @MapsId  // Avatar의 card_id는 Card의 PK와 동일
    @JoinColumn(name = "card_id")
    private Card card;

    public Long getCard_id() {
        return card_id;
    }

    public int getFace() {
        return face;
    }

    public int getHair() {
        return hair;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getClothes() {
        return clothes;
    }

    public int getAcc() {
        return acc;
    }

    public int getBg() {
        return bg;
    }

    public int getBgColor() {
        return bgColor;
    }

    public Card getCard() {
        return card;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
