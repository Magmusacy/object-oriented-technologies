package pl.edu.agh.to.school.student;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeService;

@Service
public class StudentService {
    private final GradeService gradeService;

    public StudentService(GradeService gradeService) {
        this.gradeService = gradeService;
        IO.println("Utworzono StudentService");
    }

    public void assignGrade(Student student, Course course, double gradeValue) {
        gradeService.assignGrade(student, course, gradeValue);
    }

    @PostConstruct
    void onServiceStarted() {
        IO.println("Wystartowano serwis");
    }

    @PreDestroy
    void onServiceDestroyed() {
        IO.println("Usunięto serwis");
    }
}
