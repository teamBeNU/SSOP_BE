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
    @Column(nullable = false)
    private String card_tel;

    @Column(nullable = false)
    private LocalDate card_birth;

    @Column(nullable = false)
    private Boolean card_bSecrete;

    @Column(nullable = false)
    private String card_job;


    public CardWorker() {
        super();
    }

    public CardWorker(
            String card_name,
            String card_introduction,
            String template,
            String card_cover,
            Avatar avatar,
            String profile_image_url,
            SNS card_SNS,
            String card_email,
            String card_MBTI,
            Music card_music,
            String card_movie,
            String card_tel,
            LocalDate card_birth,
            Boolean card_bSecrete,
            String card_job
    ) {
        super(card_name, card_introduction, template, card_cover, avatar, profile_image_url, card_SNS, card_email, card_MBTI, card_music, card_movie);

        if (card_tel == null || card_tel.isBlank() ||
        card_birth == null || card_bSecrete == null ||
        card_job == null) {
            throw new IllegalArgumentException();
        }

        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_bSecrete = card_bSecrete;
        this.card_job = card_job;
    }

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
