
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Singleton;
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

    public List<Assignment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Assignment.findAll", Assignment.class).list();
        }
    }
    
    public Optional<Assignment> findLastAddedRow(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("from assignment order by id DESC", Assignment.class);
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
            session.beginTransaction();
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
    
    public Assignment update(Assignment assignment){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.load(Assignment.class, assignment.getId());
            session.update(assignment);
            return assignment;
        }
    }
    
    public boolean delete(Assignment assignment){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.delete(assignment);
            return true;
        }
    }
}

