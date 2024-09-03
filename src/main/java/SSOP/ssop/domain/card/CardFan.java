package SSOP.ssop.domain.card;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("fan")
public class CardFan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;      // card의 id

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
            String card_fan_genre,
            String card_fan_first,
            String card_fan_second,
            String card_fan_reason
    ) {

        this.card_fan_genre = card_fan_genre;
        this.card_fan_first = card_fan_first;
        this.card_fan_second = card_fan_second;
        this.card_fan_reason = card_fan_reason;
    }
}
