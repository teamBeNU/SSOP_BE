package SSOP.ssop.dto.User;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginDto {
    private String email;
    private String password;
}
