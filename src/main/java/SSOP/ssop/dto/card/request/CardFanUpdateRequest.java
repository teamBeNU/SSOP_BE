package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFanUpdateRequest {
    // 필수
    private String card_fan_genre;      // 덕질장르
    private String card_fan_first;      // 최애 차애 입덕계기
    private String card_fan_second;     // 차애
    private String card_fan_reason;     // 입덕계기
}
