package pl.edu.agh.to.school.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByIndexNumber(String indexNumber);

    @Query("SELECT AVG(g.gradeValue) FROM Student s " +
            "JOIN s.grades g " +
            "WHERE s.indexNumber = :indexNumber")
    Optional<Double> getAvgGradeByIndexNumber(@Param("indexNumber") String indexNumber);
}
