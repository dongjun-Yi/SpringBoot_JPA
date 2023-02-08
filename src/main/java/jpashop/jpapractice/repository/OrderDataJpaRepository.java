package jpashop.jpapractice.repository;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDataJpaRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    Order findOneById(Long id);

    List<Order> findAll();
}
