package jpashop.jpapractice.repository;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAllByString(OrderSearch orderSearch);

    List<Order> findAllByString_querydsl(OrderSearch orderSearch);

    List<Order> findWithMemberDelivery();

    List<Order> findWithMemberDelivery_querydsl();

    List<Order> findWithMemberDelivery(int offset, int limit);

    List<Order> findWithMemberDelivery_querydsl_withPage(Pageable pageable);

    List<Order> findAllWithItem();
    List<Order> findAllWithItem_querydsl();
}
