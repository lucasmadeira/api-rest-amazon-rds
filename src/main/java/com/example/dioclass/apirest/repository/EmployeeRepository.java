package com.example.dioclass.apirest.repository;

import com.example.dioclass.apirest.model.Employee;
import com.example.dioclass.apirest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
