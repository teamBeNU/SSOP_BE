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
    private int acc;        // 악세사리
    private int bg;         // 배경
    private int bgColor;    // 배경색

    @OneToOne
    @MapsId  // Avatar의 card_id는 Card의 PK와 동일
    @JoinColumn(name = "card_id")
    private Card card;
}
