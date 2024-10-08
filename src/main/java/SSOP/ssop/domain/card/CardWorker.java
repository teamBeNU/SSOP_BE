package SSOP.ssop.domain.card;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("worker")
public class CardWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;      // card의 id

    // 필수
    @Column(name = "card_worker_company")
    private String card_worker_company;     // 회사

    @Column(name = "card_worker_job")
    private String card_worker_job;         // 직무

    // 선택
    @Column(name = "card_worker_position")
    private String card_worker_position;    // 직위

    @Column(name = "card_worker_department")
    private String card_worker_department;  // 부서


    public CardWorker() {
        super();
    }

    public CardWorker(
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
}
