
package ru.demskv.webapplicationproject.EditorUser;

import ru.demskv.webapplicationproject.EditorUser.EditorUser;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import ru.demskv.webapplicationproject.HibernateUtil;

@Singleton
public class EditorUserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<EditorUser> allUsers() {
         try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from EditorUser", EditorUser.class).list();
        }

    }

    public Optional<EditorUser> getUser(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from EditorUser where login='"+login+"'", EditorUser.class).uniqueResultOptional();
        }
    }
}