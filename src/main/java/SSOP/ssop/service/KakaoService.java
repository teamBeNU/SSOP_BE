package SSOP.ssop.service;

import SSOP.ssop.domain.KakaoUser;
import SSOP.ssop.domain.User;
import SSOP.ssop.repository.UserRepository;
import SSOP.ssop.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class KakaoService {

    private final UserRepository userRepository;

    public KakaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 카카오 소셜로그인
    public void saveOrUpdateUser(KakaoUser kakaoUser) {
        User user = new User();
        user.setSocial_type("kakao");
        user.setUserId(kakaoUser.getId());
        user.setUser_name(kakaoUser.getName());
        user.setEmail(kakaoUser.getEmail());
        user.setUser_phone(kakaoUser.getPhone_number());

        // 생일 형식 변환
        String birthyear = kakaoUser.getBirthyear();
        String birthday = kakaoUser.getBirthday();
        LocalDate userBirth = DateUtils.parseBirthdate(birthyear, birthday);

        user.setUser_birth(userBirth);

        userRepository.save(user);
    }
}
