package SSOP.ssop.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Music {

    @Column(name = "title")
    private String title;

    @Column(name = "singer")
    private String singer;

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
