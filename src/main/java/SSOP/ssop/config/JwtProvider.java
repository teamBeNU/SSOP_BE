package SSOP.ssop.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import SSOP.ssop.domain.User;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {

    private final UserRepository userRepository;

    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    static final Long EXPIRE_TIME = 24L * 60L * 60L * 1000L; // 만료 시간 24시간

    @Value("${jwt.secret}")
    private String secretKey;

    private Algorithm getSign() {
        return Algorithm.HMAC512(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String generateJwtToken(Long userId, String email, String userName) {
        Date tokenExpiration = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        return JWT.create()
                .withSubject(email) // 토큰 이름
                .withExpiresAt(tokenExpiration)
                .withClaim("userId", userId)
                .withClaim("email", email)
                .withClaim("username", userName)
                .sign(getSign());
    }

    // 토큰 검증
    public User validToken(String jwtToken) {
        try {
            // 토큰에서 이메일 추출
            String email = JWT.require(getSign())
                    .build().verify(jwtToken).getClaim("email").asString();

            if (email == null) {
                return null;
            }

            // 토큰 만료 시간 검증
            Date expiresAt = JWT.require(getSign()).build().verify(jwtToken).getExpiresAt();
            if (!validExpiredTime(expiresAt)) {
                return null; // 만료시간이 지남
            }

            // 이메일로 사용자 조회
            Optional<User> tokenUser = userRepository.findByEmail(email);
            return tokenUser.orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 만료 시간 검증
    private boolean validExpiredTime(Date expiresAt) {
        // LocalDateTime으로 만료시간 변경
        LocalDateTime localTimeExpired = expiresAt.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 현재 시간이 만료시간의 이전
        return LocalDateTime.now().isBefore(localTimeExpired);
    }

    // 토큰으로 검색
    public Long getUserIdFromToken(String token) {
        try {
            // 토큰에서 userId 클레임 추출
            return JWT.require(getSign())
                    .build()
                    .verify(token)
                    .getClaim("userId")
                    .asLong();
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}
