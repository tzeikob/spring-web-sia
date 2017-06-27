package com.app.web;

import com.app.exc.DokumentNotFoundException;
import com.app.exc.PersonNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler({
        PersonNotFoundException.class,
        DokumentNotFoundException.class
    })
    public String handleResourceNotFound() {
        return "errors/404";
    }
}
