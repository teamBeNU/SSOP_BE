package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostMapping("/join")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @Transactional
    @GetMapping("/{user_id}")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @Transactional
    @PutMapping("/{user_id}")
    public void updateUser(@PathVariable("user_id") long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

    @Transactional
    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable("user_id") long userId, @RequestParam long id) {
        userService.deleteUser(id);
    }

}
