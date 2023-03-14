package com.example.dioclass.apirest.exceptions;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(long id){
        super("Could not find id:"+id);
    }
}
