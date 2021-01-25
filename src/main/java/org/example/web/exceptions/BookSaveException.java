package org.example.web.exceptions;

public class BookSaveException extends Exception {

    String message;

    public BookSaveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
