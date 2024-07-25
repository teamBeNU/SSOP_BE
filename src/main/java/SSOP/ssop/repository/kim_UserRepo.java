// 김영한.ver

package SSOP.ssop.repository;

import SSOP.ssop.domain.User;

import java.util.List;
import java.util.Optional;

public interface kim_UserRepo {
    User save(User user); // 저장
    Optional<User> findById(Long user_id); // user_id로 검색
//    Optional<User> findByName(String user_name); // user_name으로 검색
    Optional<User> findByEmail(String email); // 이메일로 검색
    List<User> findAll(); // 모든 저장된 내용 반환
}
