package cc.project.busapp.controllers;


import cc.project.busapp.domain.Customer;
import cc.project.busapp.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/user";

    private final CustomerService userService;

    public CustomerController(CustomerService userService) {
        this.userService = userService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return userService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getUserById(@PathVariable long id) {
        return userService.getCustomerById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createUser(@RequestBody @Valid Customer customer) {
        return userService.createXustomer(customer);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer updateUser(@Valid @PathVariable long id, @RequestBody Customer customer) {
        return userService.updateCustomer(id, customer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }
}