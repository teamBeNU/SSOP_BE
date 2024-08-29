package SSOP.ssop.dto.card.request;

import SSOP.ssop.domain.card.Avatar;

public class CardCreateRequest {

    private CardEssentialCreateRequest cardEssential;
    private CardOptionalCreateRequest cardOptional;

    private Avatar avatar;  // card_cover가 avatar일 때만 사용

    private CardStudentCreateRequest student;
    private CardWorkerCreateRequest worker;
    private CardFanCreateRequest fan;

    public CardEssentialCreateRequest getCardEssential() {
        return cardEssential;
    }

    public CardOptionalCreateRequest getCardOptional() {
        return cardOptional;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public CardStudentCreateRequest getStudent() {
        return student;
    }

    public CardWorkerCreateRequest getWorker() {
        return worker;
    }

    public CardFanCreateRequest getFan() {
        return fan;
    }

    public void setCardEssential(CardEssentialCreateRequest cardEssential) {
        this.cardEssential = cardEssential;
    }

    public void setCardOptional(CardOptionalCreateRequest cardOptional) {
        this.cardOptional = cardOptional;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setStudent(CardStudentCreateRequest student) {
        this.student = student;
    }

    public void setWorker(CardWorkerCreateRequest worker) {
        this.worker = worker;
    }

    public void setFan(CardFanCreateRequest fan) {
        this.fan = fan;
    }
}
