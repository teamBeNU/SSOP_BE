package SSOP.ssop.domain.bluetooth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user_name;
    private String status;

    public Recipient(Long id, String name, String status) {
        this.id = id;
        this.user_name = name;
        this.status = status;
    }

    public Recipient() {

    }
}
