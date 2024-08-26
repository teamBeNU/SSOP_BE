package SSOP.ssop.config;

import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.service.User.UserDetailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailService userDetailService;
    private final UserRepository userRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, UserDetailService userDetailService, UserRepository userRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // CORS 설정
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 상태 비저장 세션 관리
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 로그인 및 HTTP 기본 인증 비활성화
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider())) // JWT 인증 필터 추가
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider())) // JwtAuthorizationFilter 추가
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // 모든 요청 허용

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtProvider jwtTokenProvider() {
        return new JwtProvider(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
