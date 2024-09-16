package SSOP.ssop.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}
