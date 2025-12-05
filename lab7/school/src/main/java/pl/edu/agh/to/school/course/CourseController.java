package pl.edu.agh.to.school.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.to.school.student.StudentDAO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(course -> new CourseDTO(course.getId(), course.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/{id}/students")
    public List<StudentDAO> getStudentsForCourse(@PathVariable int id) {
        return courseService.getStudentsFromCourse(id).stream()
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
