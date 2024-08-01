package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성
    @PostMapping("/join")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    // 모든 유저 정보 출력
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    // 특정 유저 정보 출력
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("user_id") long userId) {
        UserDto userDto = userService.getUser(userId);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 유저 password 수정
    @PatchMapping("/{user_id}")
    public void updateUser(@PathVariable("user_id") long userId, @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

    // 유저 삭제
    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable("user_id") long userId) {
        userService.deleteUser(userId);
    }

}
