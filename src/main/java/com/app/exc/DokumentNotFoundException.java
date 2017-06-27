package com.app.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Dokument not found")
public class DokumentNotFoundException extends RuntimeException {

    public DokumentNotFoundException(String title) {
        super("Dokument with title '" + title + "' not found.");
    }
}
