package y88.kirill.repositories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import y88.kirill.db.HibernateSessionFactory;
import y88.kirill.models.Person;

@Component
@RequiredArgsConstructor
public class PersonRepository {

    private final HibernateSessionFactory sessionFactory;

    public Person findById(Long id){
        try(Session session = sessionFactory.getFactory().getCurrentSession()){
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.getTransaction().commit();
            return person;
        }
    }

    public Person findByName(String name){
        try(Session session = sessionFactory.getFactory().getCurrentSession()){
            session.beginTransaction();
            Person person = session.createQuery("select p from Person p where p.name =:name", Person.class)
                    .setParameter("name", name)
                    .getSingleResult();

            session.getTransaction().commit();
            return person;
        }
    }







}
