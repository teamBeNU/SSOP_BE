package SSOP.ssop.service;

import SSOP.ssop.config.JwtProvider;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.LoginDto;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 생성자 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = (BCryptPasswordEncoder) passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    // 회원가입
    public Map<String, Object> saveUser(User user) {
        // 이메일 중복확인
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return Map.of("message", "해당 이메일은 이미 등록되어 있습니다.");
        } else {
            // 비밀번호 암호화
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return Map.of("message", "회원가입이 완료되었습니다.");
        }
    }

    // 로그인
    public Map<String, Object> login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            // JWT 토큰 반환
            String jwtToken = jwtProvider.generateJwtToken(user.getUserId(), user.getEmail(), user.getUser_name());
            return Map.of("token", jwtToken);
        } else {
            return Map.of("message", "로그인 실패");
        }
    }

    // 유저 모든 정보 출력
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // 특정 유저 정보 출력
    public Map<String, Object> getUser(long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            return Map.of("user", new UserDto(user.get()));
        } else {
            return Map.of("message", "존재하지 않는 사용자입니다.");
        }
    }

    // 유저 정보 업데이트 -> 일단 pw만
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(IllegalAccessError::new);
        user.updatePassword(userDto.getPassword());
        userRepository.save(user);
    }

    // userId로 유저 삭제
    public void deleteUser(long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
