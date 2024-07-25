// 김영한.ver

package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.repository.kim_MemoryUserRepo;
import SSOP.ssop.repository.kim_UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class kim_UserService {
    private final kim_UserRepo userRepo = new kim_MemoryUserRepo();

    // 회원가입
    public Long join(User user) {
        // 같은 이름이 있는 중복 회원 X
        validateDuplicateUser(user); // 중복 회원 검증

        userRepo.save(user);
        return user.getUser_id();
    }

    // 단축키 Ctrl + Alt + m 사용해서 코드 리팩토링
    private void validateDuplicateUser(User user) {
        userRepo.findByEmail(user.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 사용중인 이메일입니다..");
                });
    }

    // 전체 회원 조회
    public List<User> findUsers() {
        return userRepo.findAll();
    }
}
