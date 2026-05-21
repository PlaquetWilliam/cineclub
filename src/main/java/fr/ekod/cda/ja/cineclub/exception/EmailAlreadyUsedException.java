package fr.ekod.cda.ja.cineclub.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email " + email + " is already used");
    }
}
