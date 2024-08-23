package SSOP.ssop.service.User;

import SSOP.ssop.config.JwtProvider;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.User.LoginDto;
import SSOP.ssop.dto.User.UserDto;
import SSOP.ssop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 생성자 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    // 회원가입
    public Map<String, Object> saveUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return Collections.singletonMap("message", "해당 이메일은 이미 등록되어 있습니다.");
        } else {
            // 비밀번호를 암호화
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return Collections.singletonMap("message", "회원가입이 완료되었습니다.");
        }
    }

    // 로그인
    public Map<String, Object> login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (user != null) {
            // 비밀번호 비교
            boolean passwordMatches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

            if (passwordMatches) {
                // JWT 토큰 생성
                String jwtToken = jwtProvider.generateJwtToken(user.getUserId(), user.getEmail(), user.getUser_name());
                return Collections.singletonMap("token", jwtToken);
            } else {
                return Collections.singletonMap("message", "로그인 실패 - 비밀번호 불일치");
            }
        } else {
            return Collections.singletonMap("message", "로그인 실패 - 사용자 없음");
        }
    }

    // 유저 모든 정보 출력
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // 특정 유저 정보 출력
    public ResponseEntity<?> getUser(long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            // UserDto 객체를 직접 반환
            return ResponseEntity.ok(new UserDto(user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }
    }

    // 유저 비밀번호 수정
    public ResponseEntity<?> updatePassword(UserDto userDto) {
        if (!userRepository.existsById(userDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        User user = userRepository.findById(userDto.getUserId()).get();
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화
        userRepository.save(user);

        return ResponseEntity.ok().body(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

    // 유저 전화번호 수정
    public ResponseEntity<?> updatePhone(UserDto userDto) {
        if (!userRepository.existsById(userDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        User user = userRepository.findById(userDto.getUserId()).get();
        user.setUser_phone(userDto.getUser_phone());
        userRepository.save(user);

        return ResponseEntity.ok().body(Map.of("message", "전화번호가 성공적으로 변경되었습니다."));
    }

    // 유저 이름 & 생년월일 수정
    public ResponseEntity<?> updateNameBirth(UserDto userDto) {
        if (!userRepository.existsById(userDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        User user = userRepository.findById(userDto.getUserId()).get();
        user.setUser_name(userDto.getUser_name());
        user.setUser_birth(userDto.getUser_birth());
        userRepository.save(user);

        return ResponseEntity.ok().body(Map.of("message", "회원 정보가 성공적으로 변경되었습니다."));
    }

    // userId로 유저 삭제
    public ResponseEntity<Map<String, String>> deleteUser(long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return ResponseEntity.ok(Map.of("message", "탈퇴되었습니다.."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }
    }

}