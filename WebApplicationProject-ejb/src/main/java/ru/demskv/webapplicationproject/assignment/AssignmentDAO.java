
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Stateless(name="AssignmentDAOEJB")
public class AssignmentDAO implements AssignmentDAOLocal {

    @PersistenceContext(unitName="mysql")
    EntityManager entityManager;
    
    @Override
    public Long countAll() {
        return entityManager.createNamedQuery("Assignment.countAll", Long.class).getSingleResult();
    }
    
    @Override
    public List<Assignment> findAll(int from, int limit, String orderBy, boolean desc, Integer filterId, String filterTopic, String filterText) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
        Root<Assignment> root = cq.from(Assignment.class);
        if(desc)
            cq.orderBy(cb.desc(root.get(orderBy)));
        else
            cq.orderBy(cb.asc(root.get(orderBy)));
        cq.select(root);
        if(filterId!=null)
            cq.where(cb.equal(root.get("id"),filterId));
        return entityManager.createQuery(cq).setFirstResult(from).setMaxResults(limit).getResultList();
     }
    
    @Override
    public Optional<Assignment> findById(int id) {
        return Optional.of(entityManager.createNamedQuery("Assignment.findById", Assignment.class).setParameter("id", id).getSingleResult());
    }
    
    @Override
    public void create(Assignment assignment){    
        entityManager.getTransaction().begin();
        entityManager.persist(assignment);
        entityManager.getTransaction().commit();
    }
    
    @Override
    public void update(Assignment assignment){
        entityManager.getTransaction().begin();
        entityManager.merge(assignment);
        entityManager.getTransaction().commit();
    }
    
    @Override
    public void deleteById(int id){
        entityManager.getTransaction().begin();
        int executeUpdate = entityManager.createNamedQuery("Assignment.deleteById").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
    }
}

