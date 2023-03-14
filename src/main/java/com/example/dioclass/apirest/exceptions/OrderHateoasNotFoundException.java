package com.example.dioclass.apirest.exceptions;

public class OrderHateoasNotFoundException extends RuntimeException{

    public OrderHateoasNotFoundException(long id){
        super("Could not find id:"+id);
    }
}
