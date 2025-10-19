package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student> {
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GradeDao gradeDao;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.create(student.firstName(), student.lastName(), student.indexNumber());
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        student.courseSet().forEach(course -> {
            course.studentSet().remove(student);
            courseDao.save(course);
        });
        student.gradeSet().forEach(gradeDao::remove);
        studentDao.remove(student);
        studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        Course course = courseDao.findByName(courseName).orElseThrow(() -> new IllegalArgumentException("This course does not exist!"));
        return course.studentSet().stream().toList();
    }
}
