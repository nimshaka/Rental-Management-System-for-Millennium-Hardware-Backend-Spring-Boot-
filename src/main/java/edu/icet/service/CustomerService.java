package edu.icet.service;

import edu.icet.dto.Customer;
import edu.icet.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();
    void addCustomer(Customer customer);
    void deleteCustomer(Integer id);
    Customer searchCustomerById(Integer id);
    void updateCustomerById(Customer customer);
    List<Customer> searchByName(String customerName);
}
