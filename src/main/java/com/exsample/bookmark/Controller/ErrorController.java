package com.exsample.bookmark.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {


    @ResponseStatus()
    @ExceptionHandler(Exception.class)
    public String noResultExceptionHandler(Exception e) {
    	return "errorPage";
    }
}
