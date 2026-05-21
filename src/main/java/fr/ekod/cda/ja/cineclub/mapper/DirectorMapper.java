package fr.ekod.cda.ja.cineclub.mapper;

import fr.ekod.cda.ja.cineclub.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.cineclub.dto.director.DirectorDTO;
import fr.ekod.cda.ja.cineclub.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorDTO toDto(Director director);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", ignore = true)
    Director toEntity(CreateDirectorDTO dto);
}
