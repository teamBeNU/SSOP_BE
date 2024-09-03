package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.CardWorker;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardWorkerResponse extends CardWorker {

    // 필수
    private String card_worker_company;     // 회사
    private String card_worker_job;         // 직무

    // 선택
    private String card_worker_position;    // 직위
    private String card_worker_department;  // 부서

    public CardWorkerResponse(
            String card_worker_company,
            String card_worker_job,
            String card_worker_position,
            String card_worker_department
    ) {
        this.card_worker_company = card_worker_company;
        this.card_worker_job = card_worker_job;
        this.card_worker_position = card_worker_position;
        this.card_worker_department = card_worker_department;
    }

    @Override
    public String getCard_worker_company() {
        return card_worker_company;
    }

    @Override
    public void setCard_worker_company(String card_worker_company) {
        this.card_worker_company = card_worker_company;
    }

    @Override
    public String getCard_worker_job() {
        return card_worker_job;
    }

    @Override
    public void setCard_worker_job(String card_worker_job) {
        this.card_worker_job = card_worker_job;
    }

    @Override
    public String getCard_worker_position() {
        return card_worker_position;
    }

    @Override
    public void setCard_worker_position(String card_worker_position) {
        this.card_worker_position = card_worker_position;
    }

    @Override
    public String getCard_worker_department() {
        return card_worker_department;
    }

    @Override
    public void setCard_worker_department(String card_worker_department) {
        this.card_worker_department = card_worker_department;
    }
}
