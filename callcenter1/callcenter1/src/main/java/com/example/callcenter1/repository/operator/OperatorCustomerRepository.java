package com.example.callcenter1.repository.operator;

import com.example.callcenter1.model.operator.OperatorCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
