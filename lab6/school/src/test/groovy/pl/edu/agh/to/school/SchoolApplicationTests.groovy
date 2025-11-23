package pl.edu.agh.to.school

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import pl.edu.agh.to.school.course.Course
import pl.edu.agh.to.school.course.CourseFactory
import pl.edu.agh.to.school.grade.GradeBook
import pl.edu.agh.to.school.student.Student
import pl.edu.agh.to.school.student.StudentService

import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
class SchoolApplicationTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeBook gradeBook;

    @MockitoBean(name = "computerNetworksCourse")
    private Course computerNetworksMock

    @MockitoBean(name = "objectOrientedProgrammingCourse")
    private Course oopMock

    @Test
    void contextLoads() {
    }

    @Test
    void gradingWorks() {
        // given
        var student = new Student("Jan", "Doe", LocalDate.now(), "434343", "Hehe@gmail.co");
        var course = new Course("Matematyka");

        // when
        studentService.assignGrade(student, course, 3.5);

        // then
        assert 1 == gradeBook.getStudentGrades().size();
        assert gradeBook.getStudentGrades().containsKey(student.getIndexNumber());
        assert 1 == gradeBook.getStudentGrades().get(student.getIndexNumber()).size();
        assert 3.5 == gradeBook
                .getStudentGrades()
                .get(student.getIndexNumber())
                .getFirst()
                .value();
    }
}
