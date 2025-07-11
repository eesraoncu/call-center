package com.example.callcenter1.service;

import com.example.callcenter1.dto.response.CustomerCallDurationResponse;
import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.model.call.CustomerCall;
import com.example.callcenter1.model.customer.Customer;
import com.example.callcenter1.repository.call.CallRecordsRepository;
import com.example.callcenter1.repository.call.CustomerCallRepository;
import com.example.callcenter1.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerCallService {

    @Autowired
    private CustomerCallRepository customerCallRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CallRecordsRepository callRecordsRepository;

    // Müşterinin tüm çağrı sürelerini getir
    public List<CustomerCallDurationResponse> getCustomerCallDurations(Integer customerId) {
        List<CustomerCall> customerCalls = customerCallRepository.findByCustomerId(customerId);
        
        return customerCalls.stream()
                .map(this::createCustomerCallDurationResponse)
                .collect(Collectors.toList());
    }

    // Müşterinin toplam çağrı süresini hesapla
    public Integer getCustomerTotalCallDuration(Integer customerId) {
        List<CustomerCall> customerCalls = customerCallRepository.findByCustomerId(customerId);
        
        return customerCalls.stream()
                .mapToInt(customerCall -> {
                    CallRecords call = callRecordsRepository.findById(customerCall.getCallId()).orElse(null);
                    return call != null ? call.getCallDuration() : 0;
                })
                .sum();
    }

    // Müşterinin ortalama çağrı süresini hesapla
    public Double getCustomerAverageCallDuration(Integer customerId) {
        List<CustomerCall> customerCalls = customerCallRepository.findByCustomerId(customerId);
        
        if (customerCalls.isEmpty()) {
            return 0.0;
        }

        int totalDuration = customerCalls.stream()
                .mapToInt(customerCall -> {
                    CallRecords call = callRecordsRepository.findById(customerCall.getCallId()).orElse(null);
                    return call != null ? call.getCallDuration() : 0;
                })
                .sum();

        return (double) totalDuration / customerCalls.size();
    }

    // Tüm müşterilerin çağrı sürelerini getir
    public List<CustomerCallDurationResponse> getAllCustomerCallDurations() {
        List<CustomerCall> allCustomerCalls = customerCallRepository.findAll();
        
        return allCustomerCalls.stream()
                .map(this::createCustomerCallDurationResponse)
                .collect(Collectors.toList());
    }

    // Müşteri-Çağrı ilişkisi oluştur
    public CustomerCall createCustomerCall(Integer customerId, Integer callId) {
        CustomerCall customerCall = new CustomerCall();
        customerCall.setCustomerId(customerId);
        customerCall.setCallId(callId);
        return customerCallRepository.save(customerCall);
    }

    // Müşteri-Çağrı ilişkisi sil
    public void deleteCustomerCall(Integer customerId, Integer callId) {
        List<CustomerCall> customerCalls = customerCallRepository.findByCustomerId(customerId);
        customerCalls.stream()
                .filter(cc -> cc.getCallId().equals(callId))
                .findFirst()
                .ifPresent(customerCallRepository::delete);
    }

    private CustomerCallDurationResponse createCustomerCallDurationResponse(CustomerCall customerCall) {
        Customer customer = customerRepository.findById(customerCall.getCustomerId()).orElse(null);
        CallRecords call = callRecordsRepository.findById(customerCall.getCallId()).orElse(null);

        if (customer == null || call == null) {
            return null;
        }

        CustomerCallDurationResponse response = new CustomerCallDurationResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getCustomerPhoneNumber(),
                call.getCallId(),
                call.getCallTime(),
                call.getCallDate(),
                call.getCallDuration()
        );

        // Toplam süre ve çağrı sayısını hesapla
        Integer totalDuration = getCustomerTotalCallDuration(customer.getCustomerId());
        Long totalCalls = customerCallRepository.countByCustomerId(customer.getCustomerId());

        response.setTotalDuration(formatDuration(totalDuration));
        response.setTotalCalls(totalCalls.intValue());

        return response;
    }

    private String formatDuration(Integer seconds) {
        if (seconds == null) return "0 saniye";
        
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        
        if (minutes == 0) {
            return remainingSeconds + " saniye";
        } else if (remainingSeconds == 0) {
            return minutes + " dakika";
        } else {
            return minutes + " dakika " + remainingSeconds + " saniye";
        }
    }
} 