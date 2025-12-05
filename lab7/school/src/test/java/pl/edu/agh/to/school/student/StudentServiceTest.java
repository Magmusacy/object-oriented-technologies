package pl.edu.agh.to.school.student;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import pl.edu.agh.to.school.grade.Grade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void getAllStudentsWorksFine() {
        Student s1 = new Student("Pawel", "Gadomski", LocalDate.now(), "123456");
        Student s2 = new Student("Lewap", "Iksmodag", LocalDate.now(), "654321");
        List<Student> allStudents = List.of(s1, s2);
        studentRepository.saveAll(allStudents);
        List<Integer> expectedIds = allStudents.stream().map(Student::getId).toList();

        List<Integer> actualIds = studentService.getStudents().stream().map(Student::getId).toList();

        assertEquals(expectedIds, actualIds);
    }

    @Test
    public void addGradeWorks() {
        Student s1 = new Student("Pawel", "Gadomski", LocalDate.now(), "123456");
        Student s2 = new Student("Lewap", "Iksmodag", LocalDate.now(), "654321");
        List<Student> allStudents = List.of(s1, s2);
        studentRepository.saveAll(allStudents);

        Grade newGrade = studentService.addGrade("123456", 5);

        assertEquals(5, newGrade.getGradeValue());
    }

    @Test
    public void getAvgWorks() {
        Student s1 = new Student("Pawel", "Gadomski", LocalDate.now(), "123456");
        Student s2 = new Student("Lewap", "Iksmodag", LocalDate.now(), "654321");
        List<Student> allStudents = List.of(s1, s2);
        studentRepository.saveAll(allStudents);

        assertEquals(0, studentService.getAverageGrade("123456"));
        Grade newGrade = studentService.addGrade("123456", 5);
        assertEquals(5, studentService.getAverageGrade("123456"));
    }
}