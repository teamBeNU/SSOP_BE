package SSOP.ssop.dto.LinkShare;

public class LinkResponseDto {
    private String link;
    private String expiryTime;

    public LinkResponseDto(String link, String expiryTime) {
        this.link = link;
        this.expiryTime = expiryTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
}
