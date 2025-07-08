package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.OperatorCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorCallRepository extends JpaRepository<OperatorCall, Long> {
    // Bir operatörün tüm çağrılarını getir
    List<OperatorCall> findByOperatorId(Long operatorId);

    // Bir çağrıya bağlı tüm operatör ilişkilerini getir
    List<OperatorCall> findByCallId(Long callId);
    // Bir operatörün belirli bir çağrıdaki durumunu güncelle
    void updateOperatorCallStatus(Long operatorId, Long callId, String status);
    // Bir operatörün çağrıdaki durumunu al
    String findOperatorCallStatus(Long operatorId, Long callId);
    // Bir operatörün çağrıdaki durumunu sil
    void deleteByOperatorIdAndCallId(Long operatorId, Long callId);
    // Bir çağrıdaki tüm operatör ilişkilerini sil
    void deleteByCallId(Long callId);
    // Bir operatörün çağrıdaki tüm ilişkilerini sil
    void deleteByOperatorId(Long operatorId);
    // Bir operatörün çağrıdaki durumunu güncelle
    void updateStatusByOperatorIdAndCallId(String status, Long operatorId, Long callId);
    // Bir operatörün çağrıdaki durumunu al
}
