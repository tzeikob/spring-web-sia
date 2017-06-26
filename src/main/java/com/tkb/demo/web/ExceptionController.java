package com.tkb.demo.web;

import com.tkb.demo.exc.DokumentNotFoundException;
import com.tkb.demo.exc.PersonNotFoundException;
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
