package com.example.callcenter1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.model.call.CustomerCall;
import com.example.callcenter1.repository.call.CallRecordsRepository;
import com.example.callcenter1.repository.call.CustomerCallRepository;
import com.example.callcenter1.repository.customer.CustomerRepository;
import com.example.callcenter1.model.customer.Customer;

@Service
public class CallService {
    @Autowired
    private CallRecordsRepository callRecordsRepository;
    @Autowired
    private CustomerCallRepository customerCallRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<CallRecords> findAll() {
        return callRecordsRepository.findAll();
    }

    public CallRecords findById(Integer id) {
        Optional<CallRecords> call = callRecordsRepository.findById(id);
        return call.orElse(null);
    }

    public CallRecords save(CallRecords call, Integer customerId) {
        CallRecords savedCall = callRecordsRepository.save(call);
        CustomerCall customerCall = new CustomerCall();
        customerCall.setCallId(savedCall.getCallId());
        customerCall.setCustomerId(customerId);
        customerCallRepository.save(customerCall);
        // Müşterinin serviceId'sini güncelle
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setServiceId(savedCall.getServiceId());
            customerRepository.save(customer);
        }
        return savedCall;
    }

    public CallRecords update(Integer id, CallRecords call, Integer customerId) {
        if (callRecordsRepository.existsById(id)) {
            call.setCallId(id);
            CallRecords updatedCall = callRecordsRepository.save(call);
            CustomerCall customerCall = customerCallRepository.findByCallId(id);
            if (customerCall == null) {
                customerCall = new CustomerCall();
                customerCall.setCallId(id);
            }
            customerCall.setCustomerId(customerId);
            customerCallRepository.save(customerCall);
            // Müşterinin serviceId'sini güncelle
            Customer customer = customerRepository.findById(customerId).orElse(null);
            if (customer != null) {
                customer.setServiceId(updatedCall.getServiceId());
                customerRepository.save(customer);
            }
            return updatedCall;
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (callRecordsRepository.existsById(id)) {
            customerCallRepository.deleteByCallId(id);
            callRecordsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
