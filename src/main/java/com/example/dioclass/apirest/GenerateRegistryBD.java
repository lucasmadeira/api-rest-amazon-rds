package com.example.dioclass.apirest;

import com.example.dioclass.apirest.model.Employee;
import com.example.dioclass.apirest.model.OrderHateoas;
import com.example.dioclass.apirest.model.Person;
import com.example.dioclass.apirest.model.Status;
import com.example.dioclass.apirest.repository.EmployeeRepository;
import com.example.dioclass.apirest.repository.OrderHateoasRepository;
import com.example.dioclass.apirest.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenerateRegistryBD implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(GenerateRegistryBD.class);

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private OrderHateoasRepository orderHateoasRepository;

    @Override
    public void run(String... args) throws Exception {
        Employee e = new Employee("Lucas", "Developer", "Brazil");
        log.info("Log of event save employee 1: " +repository.save(e));
        e = new Employee("Vanessa", "Entrepenuer", "Brazil");
        log.info("Log of event save employee 2: " +repository.save(e));

        orderHateoasRepository.save(new OrderHateoas(Status.COMPLETED,"review"));
        orderHateoasRepository.save(new OrderHateoas(Status.IN_PROGRESS,"travel"));
        orderHateoasRepository.save(new OrderHateoas(Status.IN_PROGRESS,"sale"));
    }
}
