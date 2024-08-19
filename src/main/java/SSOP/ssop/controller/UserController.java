package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.User.LoginDto;
import SSOP.ssop.dto.User.UserDto;
import SSOP.ssop.service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성
    @PostMapping("/join")
    public Map<String, Object> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 자체 로그인
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    // 모든 유저 정보 출력
    @GetMapping("/total")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    // 특정 유저 정보 출력
    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam("userId") long userId) {
        return userService.getUser(userId);
    }

    // 유저 비밀번호 수정
    @Login
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        userDto.setUserId(userId);
        return userService.updatePassword(userDto);
    }

    // 유저 전화번호 수정
    @Login
    @PatchMapping("/phone")
    public ResponseEntity<?> updatePhone(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        userDto.setUserId(userId);
        return userService.updatePhone(userDto);
    }

    // 유저 이름 & 생년월일 수정
    @Login
    @PatchMapping("/namebirth")
    public ResponseEntity<?> updateNameBirth(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        userDto.setUserId(userId);
        return userService.updateNameBirth(userDto);
    }

    @Login
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long userId) {
        return userService.deleteUser(userId);
    }
}
