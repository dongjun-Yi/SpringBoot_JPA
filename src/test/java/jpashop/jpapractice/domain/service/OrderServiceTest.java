package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.domain.Address;
import jpashop.jpapractice.domain.Item.Book;
import jpashop.jpapractice.domain.Item.Item;
import jpashop.jpapractice.domain.Item.NotEnoughStockException;
import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.domain.Order;
import jpashop.jpapractice.domain.OrderStatus;
import jpashop.jpapractice.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order order = orderRepository.findOne(orderId);

        // then
        assertEquals(OrderStatus.ORDER, order.getStatus()); //상품 주문시 상태는 ORDER
        assertEquals(1, order.getOrderItems().size()); //주문한 상품의 수가 정확해야 한다.
        assertEquals(10000 * 2, order.getTotalPrice()); //주문가격은 가격*수량이다.
        assertEquals(8, item.getStockQuantity()); //주문 수량만큼 재고가 줄어여한다.
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        }, "재고 수량 예외가 발생해야 한다.");
    }

    @Test
    void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus()); //주문 취소시 상태는 CANCEL이여야 한다.
        assertEquals(10, item.getStockQuantity()); //주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}