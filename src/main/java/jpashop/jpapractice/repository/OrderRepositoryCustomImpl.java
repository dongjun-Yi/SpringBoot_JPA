package jpashop.jpapractice.repository;

import jpashop.jpapractice.OrderSearch;
import jpashop.jpapractice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final EntityManager em;

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
        if (StringUtils.hasText(orderSearch.getMemberName())) {
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
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    @Override
    public List<Order> findWithMemberDelivery() {
        return em.createQuery("select o from Order o"
                + " join fetch o.member m"
                + " join fetch o.delivery d", Order.class
        ).getResultList();
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
    public List<Order> findAllWithItem() {

        return em.createQuery("select distinct o from Order o"
                + " join fetch o.member m"
                + " join fetch o.delivery d"
                + " join fetch o.orderItems oi"
                + " join fetch oi.item i ", Order.class
        ).getResultList();
    }
}
