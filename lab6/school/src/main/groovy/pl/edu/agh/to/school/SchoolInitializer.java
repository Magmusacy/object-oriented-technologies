package pl.edu.agh.to.school;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    private @Value("${school.app.version}") String version;

    public SchoolInitializer(StudentService studentService, @Qualifier("ComputerNetworks") Course computerNetworksCourse, List<Course> allCourses, @Qualifier("ObjectOrientedProgramming") Course course2) {
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
        IO.println("Version: %s".formatted(version));
        computerNetworksCourse.getStudents()
                .forEach(student -> studentService.assignGrade(student, computerNetworksCourse, 4.5));
//        IO.println("Mamy %d kursów".formatted(allCourses.size()));
    }
}
