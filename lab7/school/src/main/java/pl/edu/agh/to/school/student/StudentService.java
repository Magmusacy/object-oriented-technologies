package pl.edu.agh.to.school.student;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeDTO;
import pl.edu.agh.to.school.grade.GradeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


    public List<Student> getStudentWithIndexNumber(String indexNumber) {
        return studentRepository.findByIndexNumber(indexNumber);
    }

    @Transactional
    public Grade addGrade(String indexNumber, int gradeValue) {
        Student student = studentRepository.findByIndexNumber(indexNumber).getFirst();
        Grade grade = new Grade(gradeValue);
        student.giveGrade(grade);
        studentRepository.save(student);
        return grade;
    }

    public Double getAverageGrade(String indexNumber) {
        return studentRepository.getAvgGradeByIndexNumber(indexNumber).orElse(0.0);
    }
}
