package SSOP.ssop.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUser {

    @Id
    private Long id;
    private String name;
    private String email;
    private String phone_number;
    private String birthday;
    private String birthyear;
    private String social_type;
}
