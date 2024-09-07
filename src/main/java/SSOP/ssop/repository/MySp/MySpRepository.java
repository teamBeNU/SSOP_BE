package SSOP.ssop.repository.MySp;

import SSOP.ssop.domain.MySp.MySp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MySpRepository extends JpaRepository<MySp, Long> {

    // user id가 가진 마이스페이스 목록 조회
    @Query("SELECT m FROM MySp m WHERE m.user.userId = :userId")
    List<MySp> findByUserId(@Param("userId") long userId);

}
