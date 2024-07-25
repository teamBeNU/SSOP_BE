package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.userService = new UserService(jdbcTemplate);
    }

    @PostMapping("/join")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @GetMapping("/info")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/modify")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
    }

}
