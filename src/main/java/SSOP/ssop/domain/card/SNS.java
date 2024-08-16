package SSOP.ssop.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SNS {

    @Column(name = "insta")
    private String insta;

    @Column(name = "x")
    private String x;

    public String getInsta() {
        return insta;
    }

    public String getX() {
        return x;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public void setX(String x) {
        this.x = x;
    }
}
