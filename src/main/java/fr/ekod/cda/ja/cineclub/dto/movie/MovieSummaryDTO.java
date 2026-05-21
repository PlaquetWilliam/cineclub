package fr.ekod.cda.ja.cineclub.dto.movie;

public record MovieSummaryDTO(
        Integer id,
        String title,
        Integer releaseYear,
        String genre,
        Integer durationMinutes,
        String directorFullName
) {
}
