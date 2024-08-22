package SSOP.ssop.dto.TeamSp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFanDto {
    private String card_fan_genre;      // 덕질장르
    private String card_fan_first;      // 최애 차애 입덕계기
    private String card_fan_second;     // 차애
    private String card_fan_reason;     // 입덕계기

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

}
