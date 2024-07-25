package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    public UserService(JdbcTemplate jdbcTemplate) {
        userRepository = new UserRepository(jdbcTemplate);
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(User user) {
        if (userRepository.isUserNotExist(user.getUser_id())) {
            throw new IllegalStateException();
        }
        userRepository.updateUser(user);
    }

    public void deleteUser(long id) {
        if (userRepository.isUserNotExist(id)) {
            throw new IllegalArgumentException();
        }
        userRepository.deleteUser(id);
    }

}
