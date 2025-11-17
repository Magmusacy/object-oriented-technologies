package pl.edu.agh.to.school;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.StudentService;

import java.util.List;

@Service
public class SchoolInitializer {
    private final StudentService studentService;
    private final Course computerNetworksCourse;
    private final List<Course> allCourses;
    private final Course objectOrientedProgrammingCourse;

    public SchoolInitializer(StudentService studentService, @Qualifier("computerNetworksCourse") Course computerNetworksCourse, List<Course> allCourses, @Qualifier("Course2") Course course2) {
        this.studentService = studentService;
        this.computerNetworksCourse = computerNetworksCourse;
        this.allCourses = allCourses;
        this.objectOrientedProgrammingCourse = course2;
    }

//    @PostConstruct
//    void initialize() {
//        Student student1 = new Student("Kamil", "Steinbach", LocalDate.now(), "419534", "kamilsteinbach@gmail.com");
//        Course course1 = new Course("Algebra");
//
//        studentService.assignGrade(student1, course1, 4.5);
//    }

    @PostConstruct
    public void initComputerNetworksCourse() {
        computerNetworksCourse.getStudents()
                .forEach(student -> studentService.assignGrade(student, computerNetworksCourse, 4.5));
        IO.println("Mamy %d kursów".formatted(allCourses.size()));
    }
}
