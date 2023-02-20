package jpashop.jpapractice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.*;
import jpashop.jpapractice.domain.Item.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static jpashop.jpapractice.domain.Item.QItem.*;
import static jpashop.jpapractice.domain.QDelivery.*;
import static jpashop.jpapractice.domain.QMember.*;
import static jpashop.jpapractice.domain.QOrder.*;
import static jpashop.jpapractice.domain.QOrderItem.*;
import static org.springframework.util.StringUtils.*;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    @Override
    public List<Order> findAllByString_querydsl(OrderSearch orderSearch) {
        return queryFactory.selectFrom(order)
                .join(order.member, member)
                .where(orderStatus(orderSearch.getOrderStatus()), usernameEq(orderSearch.getMemberName()))
                .fetch();
    }

    private BooleanExpression orderStatus(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;
    }

    private BooleanExpression usernameEq(String name) {
        return hasText(name) ? member.name.like(name) : null;
    }


    @Override
    public List<Order> findWithMemberDelivery() {
        return em.createQuery("select o from Order o"
                + " join fetch o.member m"
                + " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    @Override
    public List<Order> findWithMemberDelivery_querydsl() {
        return queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .fetch();
    }

    @Override
    public List<Order> findWithMemberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o"
                        + " join fetch o.member m"
                        + " join fetch o.delivery d", Order.class
                ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Order> findWithMemberDelivery_querydsl_withPage(Pageable pageable) {
        return queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Order> findAllWithItem() {

        return em.createQuery("select distinct o from Order o"
                + " join fetch o.member m"
                + " join fetch o.delivery d"
                + " join fetch o.orderItems oi"
                + " join fetch oi.item i ", Order.class
        ).getResultList();
    }

    @Override
    public List<Order> findAllWithItem_querydsl() {
        return queryFactory.selectFrom(order).distinct()
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .fetch();
    }
}
