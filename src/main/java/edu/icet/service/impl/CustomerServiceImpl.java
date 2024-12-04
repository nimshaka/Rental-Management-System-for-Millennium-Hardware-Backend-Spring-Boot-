package edu.icet.service.impl;

import edu.icet.dto.Customer;
import edu.icet.entity.CustomerEntity;
import edu.icet.repository.CustomerRepository;
import edu.icet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper mapper;
    private final CustomerRepository repository;
    @Override
    public List<Customer> getAll() {
        return repository.findAll().stream()
                .map(entity -> mapper.map(entity, Customer.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addCustomer(Customer customer) {
        CustomerEntity customerEntity = mapper.map(customer, CustomerEntity.class);
        repository.save(customerEntity);
    }

    @Override
    public void deleteCustomer(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Customer searchCustomerById(Integer id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);
        return customerEntity.map(entity -> mapper.map(entity, Customer.class))
                .orElse(null);
    }

    @Override
    public void updateCustomerById(Customer customer) {
        CustomerEntity customerEntity = mapper.map(customer, CustomerEntity.class);
        repository.save(customerEntity);
    }

    @Override
    public List<Customer> searchByName(String customerName) {
        List<Customer> customers = new ArrayList<>();
        repository.findByCustomerName(customerName).forEach(entity->{
            customers.add(mapper.map(entity,Customer.class));
        });

        return customers;
    }
}
