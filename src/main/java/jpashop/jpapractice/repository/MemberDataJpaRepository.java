package jpashop.jpapractice.repository;

import jpashop.jpapractice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    Member findOneById(Long id);
}
