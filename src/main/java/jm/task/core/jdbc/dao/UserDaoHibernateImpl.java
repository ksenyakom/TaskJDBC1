package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE users (id serial NOT NULL primary key, " +
            "name varchar(45) NOT NULL, last_name varchar(45) NOT NULL, age TINYINT NOT NULL)";
    private static final String DROP = "DROP TABLE users";

    private static final String DELETE_ALL_QUERY = "DELETE FROM User";

    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table created");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            System.err.println("Table already exist");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table deleted");
        } catch (PersistenceException e) {
            session.getTransaction().rollback();
            System.err.println("Exception while removing table");
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Юзер " + name + " добавлен в базу");
        } catch (HibernateException e) {
            System.err.println("Exception while saving user");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Exception while removing by id");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            return session.createQuery(criteria).getResultList();
        } catch (HibernateException e) {
            System.err.println("Exception while getAllUsers");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.createQuery(DELETE_ALL_QUERY).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Exception while cleaning table");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
