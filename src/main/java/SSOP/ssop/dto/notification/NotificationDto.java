package SSOP.ssop.dto.notification;

public class NotificationDto {
    private Long id;
    private String title;
    private String cardName;
    private boolean accepted;

    public NotificationDto(Long id, String title, String cardName, boolean accepted) {
        this.id = id;
        this.title = title;
        this.cardName = cardName;
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
