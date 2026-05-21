package fr.ekod.cda.ja.cineclub.mapper;

import fr.ekod.cda.ja.cineclub.dto.auth.RegisterRequestDTO;
import fr.ekod.cda.ja.cineclub.dto.auth.UserDTO;
import fr.ekod.cda.ja.cineclub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequestDTO dto);
}
