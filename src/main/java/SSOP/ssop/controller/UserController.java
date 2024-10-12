package SSOP.ssop.controller;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.User.LoginDto;
import SSOP.ssop.dto.User.PasswordDto;
import SSOP.ssop.dto.User.UserDto;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> getUser(@Login Long userId) {
        return userService.getUser(userId);
    }

    // 기존 비밀번호 검증
    @PostMapping("/validate-password")
    public ResponseEntity<?> validateCurrentPassword(@Login Long userId, @RequestBody PasswordDto passwordDto) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 토큰이 없습니다"));
        }

        if (passwordDto.getCurrentPassword() == null || passwordDto.getCurrentPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "현재 비밀번호를 입력해 주세요."));
        }

        return userService.validateCurrentPassword(userId, passwordDto.getCurrentPassword());
    }

    // 새 비밀번호로 업데이트
    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Login Long userId, @RequestBody PasswordDto passwordDto) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 토큰이 없습니다"));
        }
        return userService.updatePassword(userId, passwordDto.getNewPassword());
    }

    // 유저 전화번호 수정
    @PatchMapping("/phone")
    public ResponseEntity<?> updatePhone(@Login Long userId, @RequestBody UserDto userDto) {
        if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "유효한 토큰이 없습니다"));
    }
        userDto.setUserId(userId);
        return userService.updatePhone(userDto);
    }

    // 유저 이름 & 생년월일 수정
    @PatchMapping("/namebirth")
    public ResponseEntity<?> updateNameBirth(@Login Long userId, @RequestBody UserDto userDto) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 토큰이 없습니다"));
        }
        userDto.setUserId(userId);
        return userService.updateNameBirth(userDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Login Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 토큰이 없습니다"));
        }
        return userService.deleteUser(userId);
    }
}
