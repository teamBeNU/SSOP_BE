package SSOP.ssop.repository;

import SSOP.ssop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(@Param("userId") Long userId);
    Optional<User> findByEmail (String email);

    @Query("SELECT u FROM User u WHERE u.user_name = :username")
    Optional<User> findByUser_name(@Param("username") String username);
}
