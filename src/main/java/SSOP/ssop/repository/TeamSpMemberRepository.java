package SSOP.ssop.repository;

import SSOP.ssop.domain.TeamSp.TeamSpMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamSpMemberRepository extends JpaRepository<TeamSpMember, Long> {
}