package pl.edu.agh.to.school.course;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.to.school.student.Student;

import java.time.LocalDate;

@Configuration
public class CourseFactory {

    @Bean
//    @Scope("prototype")
    public Course computerNetworksCourse() {
        var student = new Student("Piotr", "Budynek", LocalDate.of(1990, 11, 7), "22334455", "budynek@student.agh.edu.pl");
        var course = new Course("Sieci komputerowe");
        course.enrollStudent(student);
        return course;
    }

    @Bean
    @Qualifier("Course2")
    public Course objectOrientedProgrammingCourse() {
        var student = new Student("Piotr", "Budynek", LocalDate.of(1990, 11, 7), "22334455", "budynek@student.agh.edu.pl");
        var course = new Course("Sieci komputerowe");
        course.enrollStudent(student);
        return course;
    }
}
