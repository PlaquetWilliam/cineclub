package fr.ekod.cda.ja.cineclub.mapper;

import fr.ekod.cda.ja.cineclub.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.cineclub.dto.movie.MovieDTO;
import fr.ekod.cda.ja.cineclub.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.cineclub.entity.Director;
import fr.ekod.cda.ja.cineclub.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {DirectorMapper.class})
public interface MovieMapper {

    MovieDTO toDto(Movie movie);

    @Mapping(target = "directorFullName", source = "director", qualifiedByName = "directorFullName")
    MovieSummaryDTO toSummaryDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "director", ignore = true)
    Movie toEntity(CreateMovieDTO dto);

    @Named("directorFullName")
    default String directorFullName(Director director) {
        return director == null ? null : director.getFirstName() + " " + director.getLastName();
    }
}
