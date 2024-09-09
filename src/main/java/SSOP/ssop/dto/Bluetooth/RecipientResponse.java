package SSOP.ssop.dto.Bluetooth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientResponse {
    private Long userId;
    private String card_name;
    private String status;

    public RecipientResponse(Long userId, String card_name, String status) {
        this.userId = userId;
        this.card_name = card_name;
        this.status = status;
    }
}
