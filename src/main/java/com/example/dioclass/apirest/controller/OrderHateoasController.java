package com.example.dioclass.apirest.controller;


import com.example.dioclass.apirest.exceptions.EmployeeNotFoundException;
import com.example.dioclass.apirest.exceptions.OrderHateoasNotFoundException;
import com.example.dioclass.apirest.model.Employee;
import com.example.dioclass.apirest.model.OrderHateoas;
import com.example.dioclass.apirest.model.Status;
import com.example.dioclass.apirest.repository.EmployeeRepository;
import com.example.dioclass.apirest.repository.OrderHateoasRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
public class OrderHateoasController {

    private final OrderHateoasRepository repository;

    public OrderHateoasController(OrderHateoasRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<OrderHateoas>> consultAllOrderHateoas(){
        long idOrder;
        Link linkUri;
        List<OrderHateoas> list = repository.findAll();

        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        for(OrderHateoas orderHateoas:list){
            idOrder = orderHateoas.getId();
            linkUri = linkTo(methodOn(OrderHateoasController.class).consultById(idOrder)).withSelfRel();
            orderHateoas.add(linkUri);
            linkUri = linkTo(methodOn(OrderHateoasController.class).consultAllOrderHateoas()).withRel("Customer order link");
            orderHateoas.add(linkUri);
        }

        return ResponseEntity.ok(list);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderHateoas> consultById(@PathVariable Long id){
        Optional<OrderHateoas> optional = repository.findById(id);

        if(optional.isPresent()){
            OrderHateoas order = optional.get();
            order.add(linkTo(methodOn(OrderHateoasController.class).consultAllOrderHateoas()).withRel("All orders"));
            return ResponseEntity.ok(order);
        }

        throw new OrderHateoasNotFoundException(id);
    }

    @PostMapping
    public OrderHateoas saveOrderHateoas(@RequestBody OrderHateoas newOrderHateoas){
        return repository.save(newOrderHateoas);
    }

    @PutMapping("/{id}")
    public OrderHateoas updateOrder(@RequestBody OrderHateoas newOrder, @PathVariable  long id){
        return repository.findById(id).map(order -> {
            order.setDescription(newOrder.getDescription());
            order.setStatus(newOrder.getStatus());
            return repository.save(newOrder);
        }).orElseGet(() -> { newOrder.setId(id);
                return repository.save(newOrder);}
        );
    }

    @DeleteMapping("/{id}")
    public void deleteOrderHateoas(@PathVariable long id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable long id){
        OrderHateoas cancelledOrder = repository.findById(id).orElseThrow(
                () -> new OrderHateoasNotFoundException(id));
        if (cancelledOrder.getStatus() == Status.IN_PROGRESS){
            cancelledOrder.setStatus(Status.CANCELLED);
            cancelledOrder.add(linkTo(methodOn(OrderHateoasController.class).consultById(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderHateoasController.class).consultAllOrderHateoas())
                    .withRel("Complete order list"));
            repository.save(cancelledOrder);
            return new ResponseEntity<>(cancelledOrder,HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body("You can't complete the task, the order has a " +
                        cancelledOrder.getStatus() + " status");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeOrderById(@PathVariable long id){
        OrderHateoas cancelledOrder = repository.findById(id).orElseThrow(
                () -> new OrderHateoasNotFoundException(id));
        if (cancelledOrder.getStatus() == Status.IN_PROGRESS){
            cancelledOrder.setStatus(Status.COMPLETED);
            cancelledOrder.add(linkTo(methodOn(OrderHateoasController.class).consultById(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderHateoasController.class).consultAllOrderHateoas())
                    .withRel("Complete order list"));
            repository.save(cancelledOrder);
            return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body("You can't complete the task, the order has a " +
                        cancelledOrder.getStatus() + " status");
    }
}
