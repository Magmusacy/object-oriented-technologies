package pl.edu.agh.to.school.notification;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;

@Service
@Profile("test")
public class ConsoleNotificationService implements NotificationService {
    @PostConstruct
    void onServiceStarted() {
        IO.println("Wystartowano ConsoleNotificationService");
    }

    @PreDestroy
    void onServiceDestroyed() {
        IO.println("Usunięto ConsoleNotificationService");
    }

    @Override
    public void notify(Student student, Grade grade) {
        IO.println("Student: %s, otrzymał ocenę: %f z przedmiotu %s".formatted(student.getFullName(), grade.value(), grade.course().getName()));
    }
}
