package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeBook {
    private final Map<String, List<Grade>> studentGrades = new HashMap<>();

    public Grade assignGrade(Student student, Course course, double gradeValue) {
        Grade newGrade = new Grade(course, gradeValue);
        studentGrades
                .computeIfAbsent(student.getIndexNumber(), k -> new ArrayList<>())
                .add(newGrade);
        return newGrade;
    }

    @PostConstruct
    void onServiceStarted() {
        IO.println("Wystartowano GradeBook service");
    }

    @PreDestroy
    void onServiceDestroyed() {
        IO.println("Usunięto GradeBook service");
    }
}
