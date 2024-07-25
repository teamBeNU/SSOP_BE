package SSOP.ssop.repository;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.util.List;

public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원가입
    public void saveUser (User user) {
        String sql = "INSERT INTO user (user_name, user_birth, user_phone, password, email) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUser_name(), user.getUser_birth(), user.getUser_phone(), user.getPassword(), user.getEmail());
    }

    // id 검색 예외
    public boolean isUserNotExist(long id) {
        String readSql = "select * from user where user_id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    // 이메일 중복 확인
    public boolean isEmailNotExist(String email) {
        String readSql = "select * from user where email = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, email).isEmpty();
    }

    // 회원정보 수정
    public void updateUser(User user) {
        String sql = "UPDATE user SET user_name = ?, user_birth = ?, user_phone = ?, password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUser_name(), user.getUser_birth(), user.getUser_phone(), user.getPassword(), user.getUser_id());
    }

    // 회원 삭제 ( user_id로 구별 )
    public void deleteUser(long id) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<UserDto> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("user_id");
            String name = rs.getString("user_name");
            Date birth = rs.getDate("user_birth");
            String phone = rs.getString("user_phone");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String social_type = rs.getString("social_type");
            return new UserDto(id, name, birth, phone, password, email, social_type);
        });
    }
}
