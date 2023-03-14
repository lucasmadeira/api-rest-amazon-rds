package com.example.dioclass.apirest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class OrderHateoasNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(OrderHateoasNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrderHateoasNotFoundException ex){
        final String message = ex.getMessage();
        return message;
    }
}
