package com.syshology.jpa.repository;

import com.syshology.jpa.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-26
 * Time: 오후 2:26
 * Project : IntelliJ IDEA
 */
@Repository
public class ItemRepository {
    @PersistenceContext
    protected EntityManager entityManager;
    public void save(Item item){
        if (item.getId() == null) {
            entityManager.persist(item);
        }else {
            entityManager.merge(item);
        }
    }

    public Optional<Item> findOne(Long id){
        Item item = entityManager.find(Item.class, id);
        return Optional.ofNullable(item);
    }
    public List<Item> findAll(){
        return entityManager.createQuery("select i from Item  i", Item.class).getResultList();
    }

}
