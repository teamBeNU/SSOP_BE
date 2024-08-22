package SSOP.ssop.dto.card.request;

import SSOP.ssop.domain.card.Avatar;
import SSOP.ssop.domain.card.Music;
import SSOP.ssop.domain.card.SNS;

public class CardCreateRequest {

    private String card_name;
    private String card_introduction;
    private String card_template;
    private String card_cover;
    private Avatar avatar;

    private SNS card_SNS;
    private String card_email;
    private String card_MBTI;
    private Music card_music;
    private String card_movie;

    private CardStudentCreateRequest student;
    private CardWorkerCreateRequest worker;

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public String getCard_template() {
        return card_template;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public SNS getCard_SNS() {
        return card_SNS;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public Music getCard_music() {
        return card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public CardStudentCreateRequest getStudent() {
        return student;
    }

    public CardWorkerCreateRequest getWorker() {
        return worker;
    }
}
