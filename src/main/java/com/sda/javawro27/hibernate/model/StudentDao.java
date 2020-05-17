package com.sda.javawro27.hibernate.model;

import com.sda.javawro27.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// umożliwia wykonywanie operacji CRUD na modelu Student
public class StudentDao {
    public void saveOrUpdate(Student student) {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(student);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Student> studentQuery = session.createQuery("SELECT a from Student a", Student.class);
            list.addAll(studentQuery.getResultList());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public Optional<Student> findById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            // istnieje prawdopodobieństwo, że rekord nie zostanie odnaleziony
            return Optional.ofNullable(session.get(Student.class, id));
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(Student student) {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.delete(student);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            // narzędzie do tworzenia zapytań i kreaowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // obiekt reprezentujący zapytanie
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);

            // obiekt reprezentujący tableę bazodanową
            // do jakiej tabeli kierujesz nasze zapytanie?
            Root<Student> rootTable = criteriaQuery.from(Student.class);

            // wykonanie zapytanie
            criteriaQuery.select(rootTable);

            // specification
            list.addAll(session.createQuery(criteriaQuery).list());

            // poznanie uniwersalnego rozwiązania które dziła z każdą bazą danych
            // używanie klas których będziecie używać na JPA (Spring)

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Student> findByLastName(String lastName) {
        List<Student> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            // narzędzie do tworzenia zapytań i kreaowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // obiekt reprezentujący zapytanie
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);

            // obiekt reprezentujący tableę bazodanową
            // do jakiej tabeli kierujesz nasze zapytanie?
            Root<Student> rootTable = criteriaQuery.from(Student.class);

            // wykonanie zapytanie
            criteriaQuery.select(rootTable)
                    .where(cb.equal(rootTable.get("lastName"), lastName));

            // specification
            list.addAll(session.createQuery(criteriaQuery).list());

            // poznanie uniwersalnego rozwiązania które dziła z każdą bazą danych
            // używanie klas których będziecie używać na JPA (Spring)

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Student> findByAgeBetween(int ageFrom, int ageTo) {
        List<Student> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            // narzędzie do tworzenia zapytań i kreaowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // obiekt reprezentujący zapytanie
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);

            // obiekt reprezentujący tableę bazodanową
            // do jakiej tabeli kierujesz nasze zapytanie?
            Root<Student> rootTable = criteriaQuery.from(Student.class);

            // wykonanie zapytanie
            criteriaQuery.select(rootTable)
                    .where(cb.between(rootTable.get("age"), ageFrom, ageTo));

            // specification
            list.addAll(session.createQuery(criteriaQuery).list());

            // poznanie uniwersalnego rozwiązania które dziła z każdą bazą danych
            // używanie klas których będziecie używać na JPA (Spring)

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }

    public List<Student> findByAgeBehaviourAndAlive(Behaviour behaviour, boolean alive) {
        List<Student> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            // narzędzie do tworzenia zapytań i kreaowania klauzuli 'where'
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // obiekt reprezentujący zapytanie
            CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);

            // obiekt reprezentujący tableę bazodanową
            // do jakiej tabeli kierujesz nasze zapytanie?
            Root<Student> rootTable = criteriaQuery.from(Student.class);

            // wykonanie zapytanie
            criteriaQuery.select(rootTable)
                    .where(cb.and(
                            cb.equal(rootTable.get("behariour"), behaviour)),
                            cb.equal(rootTable.get("alive"), alive ? 1 : 0));
            // specification
            list.addAll(session.createQuery(criteriaQuery).list());

            // poznanie uniwersalnego rozwiązania które dziła z każdą bazą danych
            // używanie klas których będziecie używać na JPA (Spring)

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }


}
