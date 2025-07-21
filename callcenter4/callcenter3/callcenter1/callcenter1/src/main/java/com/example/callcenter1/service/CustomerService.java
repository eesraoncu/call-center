package com.example.callcenter1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.callcenter1.model.customer.Customer;
import com.example.callcenter1.repository.customer.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Integer id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setCustomerId(id);
            return customerRepository.save(customer);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        Customer customer = findById(id);
        if (customer != null) {
            customer.setCustomerActive(false); // Pasif yap
            customerRepository.save(customer); // Veritabanına kaydet
            return true;
        } else {
            return false;
        }
       
    }
}
