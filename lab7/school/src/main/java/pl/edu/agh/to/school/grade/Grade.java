package pl.edu.agh.to.school.grade;

import jakarta.persistence.*;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.Student;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int gradeValue;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Grade(int value) {
        this.gradeValue = value;
    }

    public Grade() {
    }

    public int getId() {
        return id;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
