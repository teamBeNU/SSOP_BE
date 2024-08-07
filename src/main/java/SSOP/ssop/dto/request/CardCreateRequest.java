package SSOP.ssop.dto.request;

//import SSOP.ssop.domain.card.CardTemplate;
import SSOP.ssop.domain.card.Music;
import SSOP.ssop.domain.card.SNS;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CardCreateRequest {

    private String card_name;
    private String card_introduction;
    private String template;
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private CardTemplate template;

    private SNS card_SNS;
    private String card_email;
    private String card_MBTI;
    private Music card_music;
    private String card_movie;

    private CardStudentCreateRequest cardStudentCreateRequest;

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

//    public CardTemplate getTemplate() {
//        return template;
//    }
    public String getTemplate() {
        return template;
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

    public CardStudentCreateRequest getCardStudentCreateRequest() {
        return cardStudentCreateRequest;
    }
}
