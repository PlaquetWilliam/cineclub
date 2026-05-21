package fr.ekod.cda.ja.cineclub.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Integer id) {
        super("Movie with id " + id + " not found");
    }
}
