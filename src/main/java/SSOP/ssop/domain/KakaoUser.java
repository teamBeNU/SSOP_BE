package SSOP.ssop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUser {

    @Id
    private String userid;
    private String name;
    private String email;
    private String phone;
    private String birthday;
    private String birthyear;
}
