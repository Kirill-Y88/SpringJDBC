package y88.kirill.repositories;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import y88.kirill.db.HibernateSessionFactory;
import y88.kirill.models.Person;
import y88.kirill.models.Project;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectRepository {

    private final HibernateSessionFactory sessionFactory;
    private final PersonRepository personRepository;

    public Project findById(Long id) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session.get(Project.class, id);
            session.getTransaction().commit();
            return project;
        }
    }

    public Project findByTitle(String title) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session
                    .createQuery("select p from Project p where p.title =:title", Project.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return project;
        }
    }

    public List<Project> findAll() {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Project> projectList = session
                    .createQuery("select p from Project p", Project.class).getResultList();
            session.getTransaction().commit();
            return projectList;
        }
    }

    public void create(String title) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = new Project();
            project.setTitle(title);
            session.save(project);
            session.getTransaction().commit();
        }
    }

    public void update(String oldTitle, String newTitle) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session
                    .createQuery("select p from Project p where p.title =:title", Project.class)
                    .setParameter("title", oldTitle)
                    .getSingleResult();
            project.setTitle(newTitle);
            session.getTransaction().commit();
        }
    }

    public void updateAddPerson(String titleProject, String namePerson) {
        Person person = personRepository.findByName(namePerson);
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session
                    .createQuery("select p from Project p where p.title =:title", Project.class)
                    .setParameter("title", titleProject)
                    .getSingleResult();
            project.getPersons().add(person);
            session.getTransaction().commit();
        }
    }

    public void updateRemovePerson(String titleProject, String namePerson) {
        Person person = personRepository.findByName(namePerson);
        List<Person> personList = null;
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session
                    .createQuery("select p from Project p where p.title =:title", Project.class)
                    .setParameter("title", titleProject)
                    .getSingleResult();
            personList = project.getPersons();
            personList.remove(person);
            project.setPersons(personList);
            session.saveOrUpdate(project);
            session.getTransaction().commit();
        }
    }

    public void deleteByTitle(String title) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session
                    .createQuery("select p from Project p where p.title =:title", Project.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.delete(project);
            session.getTransaction().commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.getFactory().getCurrentSession()) {
            session.beginTransaction();
            Project project = session.get(Project.class, id);
            session.delete(project);
            session.getTransaction().commit();
        }
    }


}
