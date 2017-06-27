package com.app.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Person not found")
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String id) {
        super("Person with id '" + id + "' not found.");
    }
}
