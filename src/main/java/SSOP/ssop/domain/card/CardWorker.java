package SSOP.ssop.domain.card;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("worker")
public class CardWorker extends Card {

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    // 필수
    @Column(name = "card_worker_company")
    private String card_worker_company;     // 회사

    @Column(name = "card_worker_job")
    private String card_worker_job;         // 직무

    // 산텍
    @Column(name = "card_worker_position")
    private String card_worker_position;    // 직위

    @Column(name = "card_worker_department")
    private String card_worker_department;  // 부서


    public CardWorker() {
        super();
    }

    public CardWorker(
            String card_name,
            String card_introduction,
            String card_template,
            String card_cover,
            Avatar avatar,
            String profile_image_url,
            String card_birth,
            Boolean card_bSecrete,
            String card_tel,
            String card_sns_insta,
            String card_sns_x,
            String card_email,
            String card_MBTI,
            String card_music,
            String card_movie,
            String card_hobby,
            String card_address,
            String card_worker_company,
            String card_worker_job,
            String card_worker_position,
            String card_worker_department
    ) {
        super(card_name, card_introduction, card_template, card_cover, avatar, profile_image_url, card_birth, card_bSecrete, card_tel, card_sns_insta, card_sns_x, card_email, card_MBTI, card_music, card_movie, card_hobby, card_address);

        this.card_worker_company = card_worker_company;
        this.card_worker_job = card_worker_job;
        this.card_worker_position = card_worker_position;
        this.card_worker_department = card_worker_department;
    }

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
