package SSOP.ssop.dto.TeamSp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWorkerDto {
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
}
