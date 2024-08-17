package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.CardWorker;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardWorkerResponse extends CardWorker {
    private String card_tel;
    private LocalDate card_birth;
    private String card_job;

    public CardWorkerResponse(String card_tel, LocalDate card_birth, String card_job) {
        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_job = card_job;
    }

    public String getCard_job() {
        return card_job;
    }

    public void setCard_job(String card_job) {
        this.card_job = card_job;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public void setCard_birth(LocalDate card_birth) {
        this.card_birth = card_birth;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }
}
