
package ru.demskv.webapplicationproject;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Singleton
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<EditorUser> allUsers() {
        TypedQuery<EditorUser> q = em.createQuery(
                "SELECT m FROM EditorUser m", EditorUser.class);
        List<EditorUser> l = q.getResultList();
        return l;

    }

    public EditorUser getUser(String login) {
        TypedQuery<EditorUser> q = em.createQuery("SELECT m FROM EditorUser m WHERE m.login = '"+login+"'", EditorUser.class);
        return q.getSingleResult();
    }
}
