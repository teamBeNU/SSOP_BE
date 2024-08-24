package SSOP.ssop.dto.card.request;

import java.time.LocalDate;

public class CardWorkerCreateRequest {

    private String card_tel;
    private LocalDate card_birth;
    private Boolean card_bSecrete;
    private String card_job;

    public String getCard_tel() {
        return card_tel;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public Boolean getCard_bSecrete() {
        return card_bSecrete;
    }

    public String getCard_job() {
        return card_job;
    }
}
