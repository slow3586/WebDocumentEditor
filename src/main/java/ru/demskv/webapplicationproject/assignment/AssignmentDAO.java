
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.demskv.webapplicationproject.HibernateUtil;


@Singleton
public class AssignmentDAO {

    @PersistenceContext
    EntityManager entityManager;
    
    public List<Assignment> findAll(int from, int limit) {
        return entityManager.createNamedQuery("Assignment.findAll", Assignment.class).setFirstResult(0).setMaxResults(10).getResultList();
        /*
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Assignment.findAll", Assignment.class).setFirstResult(0).setMaxResults(10).list();
        }
        */
    }
    
    public List<Assignment> findAllOrder(int from, int limit, String columnName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Assignment.findAllOrder", Assignment.class).setParameter(0, columnName).setFirstResult(from).setMaxResults(limit).list();
        }
    }
    
    public Optional<Assignment> findLastAddedRow(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT a FROM Assignment a order by a.id DESC", Assignment.class);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        }
    }

    public Optional<Assignment> findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("findById", Assignment.class).setParameter(0, id).uniqueResultOptional();
        }
    }
    
    public Optional<Assignment> create(Assignment assignment){    
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(assignment);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e){
            if(tx!=null && tx.getStatus().canRollback())
                tx.rollback();
            throw e;
        }
        return findLastAddedRow();
    }
    
    public Assignment update(Assignment newAss){
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Assignment oldAss = session.get(Assignment.class, newAss.getId());
            oldAss.setText(newAss.getText());
            oldAss.setTopic(newAss.getTopic());
            session.update(oldAss);
            session.flush();
            session.getTransaction().commit();
            return oldAss;
        } catch (Exception e){
            if(tx!=null && tx.getStatus().canRollback())
                tx.rollback();
            throw e;
        }
    }
    
    public int deleteById(int id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query q = session.createNamedQuery("Assignment.deleteById").setParameter(0, id);
            int d = q.executeUpdate();
            return d;
        }
    }
}

