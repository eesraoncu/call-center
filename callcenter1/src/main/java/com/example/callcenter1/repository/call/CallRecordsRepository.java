package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CallRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRecordsRepository extends JpaRepository<CallRecords, Long> {
    // Örneğin: Belirli bir tarihteki çağrıları getir
    List<CallRecords> findByCallDate(String callDate);

    // Çağrı saatine göre filtreleme
    List<CallRecords> findByCallTime(String callTime);
    // Çağrı durumuna göre filtreleme
    List<CallRecords> findByCallStatus(String callStatus);
    // Çağrı türüne göre filtreleme
    List<CallRecords> findByCallType(String callType);
    // Çağrı yönlendirme durumuna göre filtreleme
    List<CallRecords> findByCallForwarded(Boolean callForwarded);
    // Çağrı yönlendirme numarasına göre filtreleme
    List<CallRecords> findByForwardedNumber(String forwardedNumber);
    // Çağrı yönlendirme tarihine göre filtreleme
    List<CallRecords> findByForwardedDate(String forwardedDate);    
    // Çağrı yönlendirme saatine göre filtreleme
    List<CallRecords> findByForwardedTime(String forwardedTime);    
    
}
