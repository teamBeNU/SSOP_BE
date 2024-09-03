package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardWorkerUpdateRequest {

    // 필수
    private String card_worker_company;     // 회사
    private String card_worker_job;         // 직무

    // 선택
    private String card_worker_position;    // 직위
    private String card_worker_department;  // 부서

}
