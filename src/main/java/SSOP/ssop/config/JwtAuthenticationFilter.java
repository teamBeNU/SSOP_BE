package SSOP.ssop.config;

import SSOP.ssop.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    // 생성자 직접 정의
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    // 인증 요청 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청 본문에서 사용자 정보를 추출
            ObjectMapper om = new ObjectMapper();
            LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), // 이메일을 사용자 이름으로 사용
                    loginDto.getPassword()
            );

            // AuthenticationManager를 통해 인증 시도
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }

    // 인증 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 인증된 사용자 정보 가져오기
        UserDetail userDetail = (UserDetail) authResult.getPrincipal();

        Long id = userDetail.getUser().getUserId();
        String email = userDetail.getUser().getEmail();
        String userName = userDetail.getUser().getUser_name();

        // JWT 토큰 생성
        String jwtToken = jwtProvider.generateJwtToken(id, email, userName);
        // 응답 헤더에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + jwtToken);

        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }
}
