package fr.ekod.cda.ja.cineclub.dto.auth;

import fr.ekod.cda.ja.cineclub.entity.Role;

public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
