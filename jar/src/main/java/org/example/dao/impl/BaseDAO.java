package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.example.dao.inter.DAO;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BaseDAO<P,T>  implements DAO<P,T> {

    protected Class<P> clazz;

    @PersistenceContext
    @Getter
    protected EntityManager em;

    @Override
    public void add(P p){
        em.persist(p);
    }

    @Override
    public void update(P p) {
        em.merge(p);
    }

    @Override
    public P getById(T id) {
        return em.find(clazz, id);
    }

    @Override
    public void delete(T id){
        em.remove(em.find(clazz, id));
    }
}
