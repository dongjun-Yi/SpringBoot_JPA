package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.Delivery;
import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.domain.Order;
import jpashop.jpapractice.domain.OrderItem;
import jpashop.jpapractice.repository.ItemRepository;
import jpashop.jpapractice.repository.MemberRepository;
import jpashop.jpapractice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); //order를 저장하면 delivery정보랑 orderItem정보도 같이 저장된다. 그 이유는
        //order엔티티안에 delivery랑 orderItem이 cascade All로  설정되어 order를 저장하면 같이 persist되는 것이다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}
