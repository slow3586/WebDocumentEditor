
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.lang.reflect.Field;
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
    
    public Long countAll() {
        return entityManager.createNamedQuery("Assignment.countAll", Long.class).getSingleResult();
    }
    
    public List<Assignment> findAll(int from, int limit, String orderBy, boolean desc) {
        /*
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Assignment> cr = cb.createQuery(Assignment.class);
        Root<Assignment> root = cr.from(Assignment.class);
        if(orderBy!=null){
            if(desc)
        }
        cr.orderBy(cb.desc(root.get(orderBy)));
        cr.select(root);
        return entityManager.createQuery(cr).setFirstResult(from).setMaxResults(limit).getResultList();
*/
        String namedQueryName = desc ? "Assignment.findAllDesc" : "Assignment.findAllAsc";
        return entityManager.createNamedQuery(namedQueryName, Assignment.class).setParameter("columnName", orderBy).setFirstResult(from).setMaxResults(limit).getResultList();        
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
        entityManager.getTransaction().begin();
        int executeUpdate = entityManager.createNamedQuery("Assignment.deleteById").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
        return executeUpdate;
    }
}

