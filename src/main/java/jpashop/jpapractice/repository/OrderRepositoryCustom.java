package jpashop.jpapractice.repository;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAllByString(OrderSearch orderSearch);

    List<Order> findWithMemberDelivery();

    List<Order> findWithMemberDelivery(int offset, int limit);

    List<Order> findAllWithItem();
}
