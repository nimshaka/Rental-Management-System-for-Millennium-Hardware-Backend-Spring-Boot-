package edu.icet.controller;

import edu.icet.dto.Customer;
import edu.icet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get-customer")
    public List<Customer> getCustomer() {
        return customerService.getAll();
    }

    @PostMapping("/add-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @GetMapping("/search-by-id/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerService.searchCustomerById(id);
    }


    @GetMapping("/search-by-name/{name}")
    public List<Customer> searchByName(@PathVariable String customerName) {
        return customerService.searchByName(customerName);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCustomerById(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/update-customer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomerById(customer);
    }
}
