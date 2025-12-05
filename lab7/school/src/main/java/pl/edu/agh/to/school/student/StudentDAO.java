package pl.edu.agh.to.school.student;

import java.time.LocalDate;

public record StudentDAO(int id, String firstname, String lastname, LocalDate birthDate, String indexNumber) {
}
