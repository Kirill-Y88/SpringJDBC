package y88.kirill.repositories;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import y88.kirill.db.HibernateSessionFactory;
import y88.kirill.models.Position;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionRepository {

    private final HibernateSessionFactory sessionFactory;

    public Position findById(Long id){
        try(Session session = sessionFactory.getFactory().getCurrentSession()){
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            session.getTransaction().commit();
            return position;
        }
    }

    public Position findByTitle(String title){
        try(Session session = sessionFactory.getFactory().getCurrentSession()){
            session.beginTransaction();
            Position position = session
                    .createQuery("select p from Position p where p.title =:title", Position.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return position;
        }
    }

    public List<Position> findAll(){
        try(Session session = sessionFactory.getFactory().getCurrentSession()){
            session.beginTransaction();
            List<Position> positionList = session
                    .createQuery("select p from Position p", Position.class).getResultList();
            session.getTransaction().commit();
            return positionList;
        }
    }

    public void create(String title) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Position position = new Position();
            position.setTitle(title);
            session.save(position);
            session.getTransaction().commit();
        }
    }

    public void update(String oldTitle, String newTitle) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Position position = session
                    .createQuery("select p from Position p where p.title =:title", Position.class)
                    .setParameter("title", oldTitle)
                    .getSingleResult();
            position.setTitle(newTitle);
            session.getTransaction().commit();
        }
    }

    public void deleteByTitle(String title) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Position position = session
                    .createQuery("select p from Position p where p.title =:title", Position.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.delete(position);
            session.getTransaction().commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Position position = session.get(Position.class, id);
            session.delete(position);
            session.getTransaction().commit();
        }
    }

}
