package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.notification.NotificationService;
import pl.edu.agh.to.school.student.Student;

@Service
public class GradeService {
    private final NotificationService notificationService;
    private final GradeBook gradeBook;

    public GradeService(GradeBook gradeBook, NotificationService notificationService) {
        this.gradeBook = gradeBook;
        this.notificationService = notificationService;
    }

    public void assignGrade(Student student, Course course, double gradeValue) {
        Grade grade = gradeBook.assignGrade(student, course, gradeValue);
        notificationService.notify(student, grade);
    }

    @PostConstruct
    void onServiceStarted() {
        IO.println("Wystartowano Grade service");
    }

    @PreDestroy
    void onServiceDestroyed() {
        IO.println("Usunięto grade service");
    }
}
