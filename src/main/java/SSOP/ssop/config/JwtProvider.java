package SSOP.ssop.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import SSOP.ssop.domain.User;
import SSOP.ssop.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {

    private final UserRepository userRepository;

    // UserRepository를 인자로 받는 생성자
    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    static Long EXPIRE_TIME = 24L * 60L * 60L * 1000L; // 만료 시간 24시간

    @Value("${jwt.secret}")
    private String secretKey;

    private Algorithm getSign(){
        return Algorithm.HMAC512(secretKey);
    }

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String generateJwtToken(Long UserId, String email, String user_name){

        Date tokenExpiration = new Date(System.currentTimeMillis() + (EXPIRE_TIME));

        String jwtToken = JWT.create()
                .withSubject(email) // 토큰 이름
                .withExpiresAt(tokenExpiration)
                .withClaim("userId", UserId)
                .withClaim("email", email)
                .withClaim("username", user_name)
                .sign(this.getSign());

        return jwtToken;
    }

    // 토큰 검증
    public User validToken(String jwtToken){
        try {
            String email = JWT.require(this.getSign())
                    .build().verify(jwtToken).getClaim("email").asString();

            if (email == null){
                return null;
            }
            // EXPIRE_TIME이 지났는지 검증
            Date expiresAt = JWT.require(this.getSign()).acceptExpiresAt(EXPIRE_TIME).build().verify(jwtToken).getExpiresAt();
            if (!this.validExpiredTime(expiresAt)) {
                return null; // 만료시간이 지남
            }
            Optional<User> tokenUser = userRepository.findByEmail(email);
            return tokenUser.orElse(null);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 만료 시간 검증
    private boolean validExpiredTime(Date expiresAt){
        // LocalDateTime으로 만료시간 변경
        LocalDateTime localTimeExpired = expiresAt.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        // 현재 시간이 만료시간의 이전
        return LocalDateTime.now().isBefore(localTimeExpired);
    }
}