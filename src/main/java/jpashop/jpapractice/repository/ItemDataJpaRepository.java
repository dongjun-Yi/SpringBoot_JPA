package jpashop.jpapractice.repository;

import jpashop.jpapractice.domain.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDataJpaRepository extends JpaRepository<Item, Long> {
    Item findOneById(Long id);
}
