package SSOP.ssop.dto.User;

import SSOP.ssop.domain.card.CardSaveDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
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

}
