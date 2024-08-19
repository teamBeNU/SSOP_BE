package SSOP.ssop.dto;

public class ShareLinkRequestDto {
    private String linkId;
    private String action;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
