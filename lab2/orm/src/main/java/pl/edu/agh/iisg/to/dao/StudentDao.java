package pl.edu.agh.iisg.to.dao;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(SessionService sessionService) {
        super(sessionService, Student.class);
    }

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        Student student = new Student(firstName, lastName, indexNumber);
        return save(student);
    }

    public List<Student> findAll() {
        List<Student> students = currentSession()
                .createQuery("Select s from Student s ORDER BY s.lastName", Student.class)
                .getResultList();
        return students;
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        Student student = currentSession()
                .createQuery("Select s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                .setParameter("indexNumber", indexNumber)
                .getSingleResultOrNull();
        return Optional.ofNullable(student);
    }
}
