package com.example.dioclass.apirest.controller;


import com.example.dioclass.apirest.exceptions.EmployeeNotFoundException;
import com.example.dioclass.apirest.model.Employee;
import com.example.dioclass.apirest.model.Person;
import com.example.dioclass.apirest.repository.EmployeeRepository;
import com.example.dioclass.apirest.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> consultAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee consultById(@PathVariable Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee newEmploye){
        return employeeRepository.save(newEmploye);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee newEmploye, @PathVariable  long id){
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(newEmploye.getName());
            employee.setAddres(newEmploye.getAddres());
            employee.setRole(newEmploye.getRole());
            return employeeRepository.save(employee);
        }).orElseGet(() -> { newEmploye.setId(id);
                return employeeRepository.save(newEmploye);}
        );
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id){
        employeeRepository.deleteById(id);
    }
}
