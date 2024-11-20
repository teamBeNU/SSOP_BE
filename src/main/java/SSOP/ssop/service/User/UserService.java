package SSOP.ssop.service.User;

import SSOP.ssop.config.JwtProvider;
import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.User.LoginDto;
import SSOP.ssop.dto.User.UserDto;
import SSOP.ssop.dto.card.response.CardSaveResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.MySp.MySpRepository;
import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.service.MySp.MySpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CardRepository cardRepository;
    private final MySpService mySpService;
    private final MySpRepository mySpRepository;


    // 생성자 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CardRepository cardRepository, SSOP.ssop.service.MySp.MySpService mySpService, MySpRepository mySpRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cardRepository = cardRepository;
        this.mySpService = mySpService;
        this.mySpRepository = mySpRepository;
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
            user.setRole("USER");
            userRepository.save(user);
            return Collections.singletonMap("message", "회원가입이 완료되었습니다.");
        }
    }

    // 로그인
    public Map<String, Object> login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (user != null) {
            // 카카오 로그인인 경우
            if(user.getSocial_type() != null && user.getSocial_type().equals("kakao")){
                String jwtToken = jwtProvider.generateJwtToken(user.getUserId(), user.getEmail(), user.getUser_name());
                return Collections.singletonMap("token", jwtToken);
            }

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
    public ResponseEntity<?> getUser(Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "유효한 사용자 ID가 없습니다."));
        }

        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            // UserDto 객체로 변환하여 반환
            UserDto userDto = new UserDto(userOptional.get());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }
    }

    // 기존 비밀번호 검증
    public ResponseEntity<?> validateCurrentPassword(Long userId, String currentPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "기존 비밀번호가 일치하지 않습니다."));
        }

        return ResponseEntity.ok().body(Map.of("message", "기존 비밀번호가 확인되었습니다."));
    }

    // 유저 비밀번호 수정
    public ResponseEntity<?> updatePassword(Long userId, String newPassword) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }

        User user = userRepository.findById(userId).get();
        user.setPassword(passwordEncoder.encode(newPassword)); // 비밀번호 암호화
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
//        if (!userRepository.existsById(userDto.getUserId())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
//        }

        User user = userRepository.findById(userDto.getUserId()).get();
        // body에서 요청하지 않았다면 기존 값 유지
        if (userDto.getUser_name() != null) {
            user.setUser_name(userDto.getUser_name());
        }
        if (userDto.getUser_birth() != null) {
            user.setUser_birth(userDto.getUser_birth());
        }
        userRepository.save(user);

        return ResponseEntity.ok().body(Map.of("message", "회원 정보가 성공적으로 변경되었습니다."));
    }

    // userId로 유저 삭제
    public ResponseEntity<Map<String, String>> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return ResponseEntity.ok(Map.of("message", "탈퇴되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "존재하지 않는 사용자입니다."));
        }
    }

    // 카드 저장
    public CardSaveResponse addCardToSavedList(Long userId, Long cardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 아이디입니다 : " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드 아이디입니다 : " + cardId));

        if (card.getUserId() == userId) {
            throw new IllegalArgumentException("본인 카드입니다.");
        }

        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        if (savedCardList.containsKey(cardId)) {
            return new CardSaveResponse(false, "이미 저장된 카드입니다");
        } else {
            savedCardList.put(cardId, LocalDateTime.now());
            userRepository.save(user);
            return new CardSaveResponse(true, "카드가 저장되었습니다");
        }

    }

    // 저장된 카드 삭제
    @Transactional
    public void deleteSavedCard(Long userId, List<Long> cardIds) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 아이디입니다 : " + userId));

        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        for (Long cardId : cardIds) {
            // 저장된 카드 목록에서 카드 삭제
            if (savedCardList.remove(cardId) != null) {
                // 그룹에서 해당 카드 제거
                List<MySp> groups = mySpRepository.findByUserId(userId);

                for (MySp group : groups) {
                    // 그룹 내 카드 제거
                    group.getCards().removeIf(card -> {
                        if (card.getCardId().equals(cardId)) {
                            card.setMySp(null); // 관계 해제
                            return true;
                        }
                        return false;
                    });
                }

                // 그룹 상태 저장
                mySpRepository.saveAll(groups);
            } else {
                throw new IllegalArgumentException("저장한 카드가 아닙니다. 카드 ID: " + cardId);
            }
        }

        // 사용자 변경 사항 저장
        userRepository.save(user);
    }

}
