package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.*;
import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    //private final MemberRepository memberRepository;
    private final MemberDataJpaRepository memberDataJpaRepository;
    //private final OrderRepository orderRepository;
    //private final ItemRepository itemRepository;

    private final ItemDataJpaRepository itemDataJpaRepository;
    private final OrderDataJpaRepository orderDataJpaRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        //Member member = memberRepository.findOne(memberId);
        Member member = memberDataJpaRepository.findOneById(memberId);
        //Item item = itemRepository.findOne(itemId);
        Item item = itemDataJpaRepository.findOneById(itemId);


        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderDataJpaRepository.save(order); //order를 저장하면 delivery정보랑 orderItem정보도 같이 저장된다. 그 이유는
        //order엔티티안에 delivery랑 orderItem이 cascade All로  설정되어 order를 저장하면 같이 persist되는 것이다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //Order order = orderRepository.findOne(orderId);
        Order order = orderDataJpaRepository.findOneById(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderDataJpaRepository.findAllByString(orderSearch);
    }

    public List<Order> findOrdersWithMemberDelivery() {
        return orderDataJpaRepository.findWithMemberDelivery_querydsl();
    }

    public List<Order> findOrdersWithMemberDelivery(Pageable pageable) {
        return orderDataJpaRepository.findWithMemberDelivery_querydsl_withPage(pageable);
    }

    public List<Order> findAllOrders() {
        return orderDataJpaRepository.findAll();
    }

    public List<Order> findALlWithItem() {
        return orderDataJpaRepository.findAllWithItem_querydsl();
    }
}
