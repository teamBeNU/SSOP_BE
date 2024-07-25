package SSOP.ssop.dto;

import java.sql.Date;

// 어노테이션 추가하기
public class UserDto {
    private String user_name;
    private Long user_id;
    private Date user_birth;
    private String user_tel;
    private String password;
    private String email;
    private String social_type;

    public UserDto(long user_id, String user_name, Date user_birth, String user_tel, String password, String email, String social_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_birth = user_birth;
        this.user_tel = user_tel;
        this.password = password;
        this.email = email;
        this.social_type = social_type;
    }
}
