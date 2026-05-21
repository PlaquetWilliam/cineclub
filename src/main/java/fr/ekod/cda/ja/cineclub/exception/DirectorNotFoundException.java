package fr.ekod.cda.ja.cineclub.exception;

public class DirectorNotFoundException extends RuntimeException {
    public DirectorNotFoundException(Integer id) {
        super("Director with id " + id + " not found");
    }
}
