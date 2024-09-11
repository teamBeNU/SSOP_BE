package SSOP.ssop.dto.User;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.CardSaveDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long userId;
    private String user_name;
    private String email;
    private String password;
    private LocalDate user_birth;
    private String user_phone;
    private String social_type;
    private Map<Long, CardSaveDetails> saved_card_list;

    public UserDto() {
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user_birth = user.getUser_birth();
        this.user_phone = user.getUser_phone();
        this.social_type = user.getSocial_type();
        this.saved_card_list = user.getSaved_card_list();
    }

}
