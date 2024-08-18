package SSOP.ssop.repository;

import SSOP.ssop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(@Param("userId") Long userId);
    Optional<User> findByEmail (String email);

    Optional<User> findByUsername(@Param("username")String username);
}
