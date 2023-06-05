package y88.kirill.repositories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import y88.kirill.db.HibernateSessionFactory;
import y88.kirill.models.Person;
import y88.kirill.models.Position;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final HibernateSessionFactory sessionFactory;
    private final PositionRepository positionRepository;

    public Person findById(Long id) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.getTransaction().commit();
            return person;
        }
    }

    public Person findByName(String name) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session
                    .createQuery("select p from Person p where p.name =:name", Person.class)
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
            return person;
        }
    }

    public List<Person> findAll() {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Person> personList = session
                    .createQuery("select p from Person p", Person.class).getResultList();
            session.getTransaction().commit();
            return personList;
        }
    }

    public void create(String name, Long idPosition) {
        Position position = positionRepository.findById(idPosition);
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = new Person();
            person.setName(name);
            person.setPosition(position);
            session.save(person);
            session.getTransaction().commit();
        }
    }

    public void updateName(String oldName, String newName) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session
                    .createQuery("select p from Person p where p.name =:name", Person.class)
                    .setParameter("name", oldName)
                    .getSingleResult();
            person.setName(newName);
            session.getTransaction().commit();
        }
    }

    public void updatePosition(String name, String positionTitle) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            Position position = positionRepository.findByTitle(positionTitle);
            session.beginTransaction();
            Person person = session
                    .createQuery("select p from Person p where p.name =:name", Person.class)
                    .setParameter("name", name)
                    .getSingleResult();
            person.setPosition(position);
            session.saveOrUpdate(person);
            session.getTransaction().commit();
        }
    }

    public void deleteByName(String name) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session
                    .createQuery("select p from Person p where p.name =:name", Person.class)
                    .setParameter("name", name)
                    .getSingleResult();
            session.delete(person);
            session.getTransaction().commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.delete(person);
            session.getTransaction().commit();
        }
    }


}
