package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CustomerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCallRepository extends JpaRepository<CustomerCall, Integer> {
    
    // Müşterinin tüm çağrılarını getir
    @Query("SELECT cc FROM CustomerCall cc WHERE cc.customerId = :customerId")
    List<CustomerCall> findByCustomerId(@Param("customerId") Integer customerId);
    
    // Belirli çağrının müşterilerini getir
    @Query("SELECT cc FROM CustomerCall cc WHERE cc.callId = :callId")
    List<CustomerCall> findByCallId(@Param("callId") Integer callId);
    
    // Müşterinin çağrı sayısını getir
    @Query("SELECT COUNT(cc) FROM CustomerCall cc WHERE cc.customerId = :customerId")
    Long countByCustomerId(@Param("customerId") Integer customerId);
}
