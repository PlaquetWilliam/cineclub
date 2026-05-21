package fr.ekod.cda.ja.cineclub.dto.director;

import java.time.LocalDate;

public record DirectorDTO(
        Integer id,
        String firstName,
        String lastName,
        String nationality,
        LocalDate birthDate
) {
}
