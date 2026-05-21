package fr.ekod.cda.ja.cineclub.dto.auth;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) {
}
