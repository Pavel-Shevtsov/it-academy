package org.example.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public abstract class AbstractJPADAO  {

    protected EntityManager em;

    public void init(){
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("org.example");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    public void close(){
        if (em.getTransaction().isActive()){
            em.getTransaction().commit();
        }
        em.getEntityManagerFactory().close();
        em.close();
    }

}
