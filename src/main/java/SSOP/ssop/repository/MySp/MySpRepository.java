package SSOP.ssop.repository.MySp;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MySpRepository extends JpaRepository<MySp, Long> {

    // user id가 가진 마이스페이스 목록 조회
    @Query("SELECT m FROM MySp m WHERE m.user.userId = :userId")
    List<MySp> findByUserId(@Param("userId") long userId);

    // 특정 유저의 그룹을 찾는 쿼리 메서드
    @Query("SELECT m FROM MySp m WHERE m.groupId = :groupId AND m.user.userId = :userId")
    Optional<MySp> findByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);
}
