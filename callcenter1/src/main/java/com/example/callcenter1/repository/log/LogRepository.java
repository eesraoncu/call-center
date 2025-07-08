package com.example.callcenter1.repository.log;

import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.model.call.CallRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    // Bir çağrıya ait tüm logları getir
    List<Log> findByCallRecords(CallRecords callRecords);

    // veya çağrı id'si ile
    List<Log> findByCallRecords_CallId(Long callId);
    // Logları çağrıya göre sıralı olarak getir
    List<Log> findByCallRecordsOrderByTimestampAsc(CallRecords callRecords);
    // veya çağrı id'sine göre sıralı olarak
    List<Log> findByCallRecords_CallIdOrderByTimestampAsc(Long callId);
    // Logları çağrıya göre ters sıralı olarak getir
    List<Log> findByCallRecordsOrderByTimestampDesc(CallRecords callRecords);
    // veya çağrı id'sine göre ters sıralı olarak
    List<Log> findByCallRecords_CallIdOrderByTimestampDesc(Long callId);    
    // Logları çağrıya göre en son logu getir
    Log findTopByCallRecordsOrderByTimestampDesc(CallRecords callRecords);
    // veya çağrı id'sine göre en son logu getir
    Log findTopByCallRecords_CallIdOrderByTimestampDesc(Long callId);
    // Logları çağrıya göre en eski logu getir
    Log findTopByCallRecordsOrderByTimestampAsc(CallRecords callRecords);
    // veya çağrı id'sine göre en eski logu getir
    Log findTopByCallRecords_CallIdOrderByTimestampAsc(Long callId);
    
}
