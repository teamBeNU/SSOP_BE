package SSOP.ssop.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Music {

    @Column(name = "title")
    private String title;

    @Column(name = "singer")
    private String singer;

}
