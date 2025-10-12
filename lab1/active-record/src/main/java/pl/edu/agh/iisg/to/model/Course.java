package pl.edu.agh.iisg.to.model;

import pl.edu.agh.iisg.to.executor.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Course {

    public static final String TABLE_NAME = "course";

    private static final Logger logger = Logger.getGlobal();

    private final int id;

    private final String name;

    private List<Student> enrolledStudents;

    private boolean isStudentsListDownloaded = false;

    Course(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static Optional<Course> create(final String name) {
        String insertSql = "INSERT INTO course (name) VALUES (?);";
        Object[] args = {
                name
        };

        try {
            int id = QueryExecutor.createAndObtainId(insertSql, args);
            return Course.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Course> findById(final int id) {
        String findByIdSql = "SELECT * FROM course WHERE id = ?";
        Object[] args = {
                id
        };

        try (ResultSet rs = QueryExecutor.read(findByIdSql, args)) {
            if (rs.next()) {
                return Optional.of(new Course(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean enrollStudent(final Student student) {
        String isAlreadyEnrolledSql =  "SELECT * FROM student_course WHERE student_id = (?) and course_id = (?)";
        Object[] argsIsAlreadyEnrolled = { student.id(), this.id };
        try (ResultSet rs = QueryExecutor.read(isAlreadyEnrolledSql, argsIsAlreadyEnrolled)) {
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        String enrollStudentSql = "INSERT INTO student_course(student_id, course_id) VALUES(?, ?)";
        Object[] args = {
            student.id(), this.id
        };

        try {
            QueryExecutor.create(enrollStudentSql, args);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> studentList() {
        String findStudentListSql = "SELECT student.id, student.first_name, student.last_name, student.index_number FROM student_course " +
                "JOIN student ON student.id = student_course.student_id " +
                "WHERE student_course.course_id = ?";
        Object[] args = {
            this.id
        };

        List<Student> resultList = new LinkedList<>();
        // TODO
        try (ResultSet rs = QueryExecutor.read(findStudentListSql, args)) {
            while (rs.next()) {
                resultList.add(new Student(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("index_number")
                ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public List<Student> cachedStudentsList() {
        if (!isStudentsListDownloaded) {
            enrolledStudents = studentList();
        }
        return enrolledStudents;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        return name.equals(course.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
