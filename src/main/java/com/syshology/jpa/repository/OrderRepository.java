package com.syshology.jpa.repository;

import com.syshology.jpa.entity.Member;
import com.syshology.jpa.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 2:56
 * Project : IntelliJ IDEA
 */
@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }
    public void remove(Order order){
        entityManager.remove(order);
    }

    public Optional<Order> findOne(Long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        List<Predicate> criteria = new ArrayList<Predicate>();
        // 주문상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        // 회원이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            //회원과 조인
            Join<Order, Member> m = o.join("member", JoinType.INNER);
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = entityManager.createQuery(cq).setMaxResults(1000);//최대 1000건
        return query.getResultList();
    }
}
