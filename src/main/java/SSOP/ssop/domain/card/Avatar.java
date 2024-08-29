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
}
