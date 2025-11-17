package pl.edu.agh.school;

import java.util.List;

public interface PersistanceManager {
    void saveTeachers(List<Teacher> teacherList);
    List<Teacher> loadTeachers();
    void saveClasses(List<SchoolClass> schoolClasses);
    List<SchoolClass> loadClasses();
}
