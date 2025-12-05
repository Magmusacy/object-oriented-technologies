package pl.edu.agh.to.school.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDAO> getStudents(@RequestParam(required = false) String indexNumber) {
        if (indexNumber == null) {
            return studentService.getStudents().stream()
                    .map(student -> new StudentDAO(
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getBirthDate(),
                            student.getIndexNumber()
                    ))
                    .toList();
        } else {
            return studentService.getStudentWithIndexNumber(indexNumber).stream()
                    .map(student -> new StudentDAO(
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getBirthDate(),
                            student.getIndexNumber()
                    ))
                    .toList();
        }
    }

    @PostMapping(path = "/grade")
    public GradeDTO gradeStudent(@RequestBody GradeRequestDTO requestDTO) {
        Grade grade = studentService.addGrade(requestDTO.indexNumber(), requestDTO.gradeValue());
        return new GradeDTO(requestDTO.indexNumber(), grade.getGradeValue());
    }

    @GetMapping(path = "/grade/{indexNumber}")
    public Double getAverageGradeForStudent(@PathVariable String indexNumber) {
        return studentService.getAverageGrade(indexNumber);
    }
}
