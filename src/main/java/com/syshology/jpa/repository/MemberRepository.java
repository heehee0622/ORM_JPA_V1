package com.syshology.jpa.repository;

import com.syshology.jpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오전 11:36
 * Project : IntelliJ IDEA
 */
@Repository
public class MemberRepository {
    @PersistenceContext
    protected EntityManager entityManager;
    public void save(Member member){
        entityManager.persist(member);
    }

    public Optional<Member> findOne(Long id){
        Member member = entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll(){
        return entityManager.createQuery("select  m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }
    public List<Member> findOneWithJpql(String name) {
        return entityManager.createQuery("select  m from Member m where m.name= :name", Member.class).setParameter("name", name).getResultList();

    }

}
