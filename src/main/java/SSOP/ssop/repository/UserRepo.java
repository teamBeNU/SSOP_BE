package SSOP.ssop.repository;

import SSOP.ssop.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    User save(User user); // 저장
    Optional<User> findByName(String user_name); // 이름으로 검색
    Optional<User> findByEmail(String email); // 이름으로 검색
    List<User> findAll(); // 모든 저장된 내용 반환
}
