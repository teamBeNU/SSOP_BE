package SSOP.ssop.dto.card.response;

public class CardSaveResponse {
    private boolean isAdded;
    private String message;

    public CardSaveResponse(boolean isAdded, String message) {
        this.isAdded = isAdded;
        this.message = message;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
