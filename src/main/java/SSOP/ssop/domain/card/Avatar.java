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

    // 아바타 커스텀
    private int eyes;   // 이목구비 - 눈
    private int eyebrows;   // 이목구비 - 눈썹
    private int mouth;   // 이목구비 - 입
    private int mole;       // 이목구비 - 점
    private int hairFront;   // 헤어 - 앞머리
    private int hairBack;   // 헤어 - 뒷머리
    private int hairFrontColor;  // 헤어 - 앞머리 컬러
    private int hairBackColor;  // 헤어 - 뒷머리 컬러
    private int clothes;    // 옷
    private int accEar;     // 악세사리 - 귀걸이
    private int accNose;     // 악세사리 - 코 피어싱
    private int accGlasses;     // 악세사리 - 안경
    private int accPin;     // 악세사리 - 머리핀
    private int accEtc;     // 악세사리 - 기타
    private int bg;         // 배경
    private int bgColor;    // 배경색

    @OneToOne
    @MapsId  // Avatar의 card_id는 Card의 PK와 동일
    @JoinColumn(name = "card_id")
    private Card card;

    // 아바타 값 초기화
    public void resetAvatar() {
        this.eyes = 0;
        this.eyebrows = 0;
        this.mouth = 0;
        this.mole = 0;
        this.hairFront = 0;
        this.hairBack = 0;
        this.hairFrontColor = 0;
        this.hairBackColor = 0;
        this.clothes = 0;
        this.accEar = 0;
        this.accNose = 0;
        this.accGlasses = 0;
        this.accPin = 0;
        this.accEtc = 0;
        this.bg = 0;
        this.bgColor = 0;
    }
}
