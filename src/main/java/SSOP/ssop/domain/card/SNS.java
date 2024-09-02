package SSOP.ssop.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SNS {

    @Column(name = "insta")
    private String insta;

    @Column(name = "x")
    private String x;
}
