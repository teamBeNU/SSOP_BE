package SSOP.ssop.dto.card.request;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class CardWorkerCreateRequest {

    private String card_worker_company;     // 회사
    private String card_worker_job;         // 직무
    private String card_worker_position;    // 직위
    private String card_worker_department;  // 부서

    public String getCard_worker_company() {
        return card_worker_company;
    }

    public String getCard_worker_job() {
        return card_worker_job;
    }

    public String getCard_worker_position() {
        return card_worker_position;
    }

    public String getCard_worker_department() {
        return card_worker_department;
    }

    public void setCard_worker_company(String card_worker_company) {
        this.card_worker_company = card_worker_company;
    }

    public void setCard_worker_job(String card_worker_job) {
        this.card_worker_job = card_worker_job;
    }

    public void setCard_worker_position(String card_worker_position) {
        this.card_worker_position = card_worker_position;
    }

    public void setCard_worker_department(String card_worker_department) {
        this.card_worker_department = card_worker_department;
    }
}
