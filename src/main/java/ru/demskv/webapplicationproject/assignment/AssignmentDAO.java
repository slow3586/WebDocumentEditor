
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
import ru.demskv.webapplicationproject.HibernateUtil;


@Singleton
public class AssignmentDAO {

    @PersistenceContext
    EntityManager entityManager;
    
    public Long countAll() {
        return entityManager.createNamedQuery("Assignment.countAll", Long.class).getSingleResult();
    }
    
    public List<Assignment> findAll(int from, int limit, String orderBy, boolean desc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
        Root<Assignment> root = cq.from(Assignment.class);
        if(desc)
            cq.orderBy(cb.desc(root.get(orderBy)));
        else
            cq.orderBy(cb.asc(root.get(orderBy)));
        cq.select(root);
        return entityManager.createQuery(cq).setFirstResult(from).setMaxResults(limit).getResultList();
        //String namedQueryName = desc ? "Assignment.findAllDesc" : "Assignment.findAllAsc";
        //System.out.println(from+" "+limit+" "+orderBy+" "+desc);
        //return entityManager.createNamedQuery(namedQueryName, Assignment.class).setParameter("columnName", orderBy).setFirstResult(from).setMaxResults(limit).getResultList();        
    }
    
    public Optional<Assignment> findById(int id) {
        return Optional.of(entityManager.createNamedQuery("Assignment.findById", Assignment.class).setParameter("id", id).getSingleResult());
    }
    
    public void create(Assignment assignment){    
        entityManager.getTransaction().begin();
        entityManager.persist(assignment);
        entityManager.getTransaction().commit();
    }
    
    public void update(Assignment assignment){
        entityManager.getTransaction().begin();
        entityManager.merge(assignment);
        entityManager.getTransaction().commit();
    }
    
    public void deleteById(int id){
        entityManager.getTransaction().begin();
        int executeUpdate = entityManager.createNamedQuery("Assignment.deleteById").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
    }
}

