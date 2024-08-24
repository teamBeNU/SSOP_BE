package SSOP.ssop.controller;

import SSOP.ssop.config.UserDetail;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.LoginDto;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
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
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        Long authenticatedUserId = userDetail.getUser().getUserId();

        // 인증된 사용자 ID와 요청된 사용자 ID가 일치하는지 확인
        if (authenticatedUserId.equals(userId)) {
            userDto.setUserId(userId);
            return userService.updatePassword(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "권한이 없습니다."));
        }
    }

    // 유저 전화번호 수정
    @PatchMapping("/phone")
    public ResponseEntity<?> updatePhone(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        Long authenticatedUserId = userDetail.getUser().getUserId();

        // 인증된 사용자 ID와 요청된 사용자 ID가 일치하는지 확인
        if (authenticatedUserId.equals(userId)) {
            userDto.setUserId(userId);
            return userService.updatePhone(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "권한이 없습니다."));
        }
    }

    // 유저 이름 & 생년월일 수정
    @PatchMapping("/namebirth")
    public ResponseEntity<?> updateNameBirth(@RequestParam("userId") long userId, @RequestBody UserDto userDto) {
        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        Long authenticatedUserId = userDetail.getUser().getUserId();

        // 인증된 사용자 ID와 요청된 사용자 ID가 일치하는지 확인
        if (authenticatedUserId.equals(userId)) {
            userDto.setUserId(userId);
            return userService.updateNameBirth(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "권한이 없습니다."));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long userId) {
        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        Long authenticatedUserId = userDetail.getUser().getUserId();

        // 인증된 사용자 ID와 요청된 사용자 ID가 일치하는지 확인
        if (authenticatedUserId.equals(userId)) {
            return userService.deleteUser(userId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "권한이 없습니다."));
        }
    }


}
