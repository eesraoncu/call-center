package com.example.callcenter1.repository.operator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.callcenter1.model.operator.OperatorCustomer;

@Repository
public interface OperatorCustomerRepository extends JpaRepository<OperatorCustomer, Integer> {
    // Bir operatörün konuştuğu tüm müşterilerin ID'lerini getir
    @Query("SELECT oc.customerId FROM OperatorCustomer oc WHERE oc.operatorId = :operatorId")
    List<Integer> findCustomerIdsByOperatorId(@Param("operatorId") Integer operatorId);

    // Bir müşterinin konuştuğu tüm operatörlerin ID'lerini getir
    @Query("SELECT oc.operatorId FROM OperatorCustomer oc WHERE oc.customerId = :customerId")
    List<Integer> findOperatorIdsByCustomerId(@Param("customerId") Integer customerId);

    // Bir çağrıya katılan tüm müşteri ve operatörleri getir
    List<OperatorCustomer> findByCallId(Integer callId);

    // Bir operatörün yaptığı tüm çağrılar
    List<OperatorCustomer> findByOperatorId(Integer operatorId);

    // Bir müşterinin yaptığı tüm çağrılar
    List<OperatorCustomer> findByCustomerId(Integer customerId);

    // Bir operatör, müşteri ve çağrıya göre kayıt var mı?
    boolean existsByOperatorIdAndCustomerIdAndCallId(Integer operatorId, Integer customerId, Integer callId);
// Bir operatörün konuştuğu farklı müşteri sayısı
@Query("SELECT COUNT(DISTINCT oc.customerId) FROM OperatorCustomer oc WHERE oc.operatorId = :operatorId")
long countDistinctCustomerByOperatorId(@Param("operatorId") Integer operatorId);

// Bir operatörün konuştuğu müşteri sayısı (distinct olmadan) - operatorId ile
@Query("SELECT COUNT(oc.customerId) FROM OperatorCustomer oc WHERE oc.operatorId = :operatorId")
long countCustomerByOperatorId(@Param("operatorId") Integer operatorId);

// Bir operatörün konuştuğu müşteri sayısı (distinct olmadan) - operatorEmail ile
@Query("SELECT COUNT(oc.customerId) FROM OperatorCustomer oc JOIN Operator o ON oc.operatorId = o.operatorId WHERE o.operatorEmail = :operatorEmail")
long countCustomerByOperatorEmail(@Param("operatorEmail") String operatorEmail);

// Bir müşterinin toplam çağrı sayısı (distinct olmadan)
@Query("SELECT COUNT(oc.callId) FROM OperatorCustomer oc WHERE oc.customerId = :customerId")
long countCallsByCustomerId(@Param("customerId") Integer customerId);

// Bir operatörün yaptığı toplam çağrı sayısı (distinct olmadan) - operatorEmail ile
@Query("SELECT COUNT(oc.callId) FROM OperatorCustomer oc JOIN Operator o ON oc.operatorId = o.operatorId WHERE o.operatorEmail = :operatorEmail")
long countCallsByOperatorEmail(@Param("operatorEmail") String operatorEmail);

    int countByOperatorIdAndCustomerId(Integer operatorId, Integer customerId);
    int countByCustomerId(Integer customerId);

    @Query("SELECT COUNT(DISTINCT oc.customerId) FROM OperatorCustomer oc JOIN Operator o ON oc.operatorId = o.operatorId WHERE o.operatorEmail = :operatorEmail")
    long countDistinctCustomerByOperatorEmail(@Param("operatorEmail") String operatorEmail);
}
