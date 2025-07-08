package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CustomerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCallRepository extends JpaRepository<CustomerCall, Long> {
    // Bir müşterinin tüm çağrılarını getir
    List<CustomerCall> findByCustomerId(Long customerId);

    // Bir çağrıya bağlı tüm müşteri ilişkilerini getir
    List<CustomerCall> findByCallId(Long callId);
    // Müşteri çağrılarını çağrı durumuna göre filtrele
    List<CustomerCall> findByCallStatus(String callStatus);
    // Müşteri çağrılarını çağrı türüne göre filtrele
    List<CustomerCall> findByCallType(String callType);
    // Müşteri çağrılarını çağrı tarihine göre filtrele
    List<CustomerCall> findByCallDateBetween(String startDate, String endDate);
    // Müşteri çağrılarını çağrı önceliğine göre filtrele
    List<CustomerCall> findByCallPriority(String callPriority); 
    // Müşteri çağrılarını çağrı temsilcisine göre filtrele
    List<CustomerCall> findByAgentId(Long agentId);
    // Müşteri çağrılarını çağrı kategorisine göre filtrele
    List<CustomerCall> findByCallCategory(String callCategory);
    // Müşteri çağrılarını çağrı çözüm süresine göre filtrele
    List<CustomerCall> findByResolutionTimeLessThanEqual(Integer resolutionTime);
    // Müşteri çağrılarını çağrı çözüm durumuna göre filtrele
    List<CustomerCall> findByResolutionStatus(String resolutionStatus);
    // Müşteri çağrılarını çağrı önceliğine göre ve çağrı duruma göre filtrele
    List<CustomerCall> findByCallPriorityAndCallStatus(String callPriority, String callStatus);
    // Müşteri çağrılarını çağrı türüne göre ve çağrı duruma göre filtrele
    List<CustomerCall> findByCallTypeAndCallStatus(String callType, String callStatus); 
}
