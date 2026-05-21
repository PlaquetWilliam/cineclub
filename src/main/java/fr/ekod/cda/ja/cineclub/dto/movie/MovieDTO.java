package fr.ekod.cda.ja.cineclub.dto.movie;

import fr.ekod.cda.ja.cineclub.dto.director.DirectorDTO;

public record MovieDTO(
        Integer id,
        String title,
        Integer releaseYear,
        Integer durationMinutes,
        String genre,
        String synopsis,
        DirectorDTO director
) {
}
