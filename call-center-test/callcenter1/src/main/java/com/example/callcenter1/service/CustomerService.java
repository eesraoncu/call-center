package com.example.callcenter1.service;

import com.example.callcenter1.model.customer.Customer;
import com.example.callcenter1.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() { return customerRepository.findAll(); }
    public Customer saveCustomer(Customer customer) { return customerRepository.save(customer); }
    public void deleteCustomer(Integer id) { customerRepository.deleteById(id); }
}
