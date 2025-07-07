package com.example.callcenter1.repository.operator;

import com.example.callcenter1.model.operator.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {
    Optional<Operator> findByOperatorName(String operatorName);
}

//save(Operator operator)           // Operator kaydet/güncelle
//findAll()                 // Tüm operatorleri getir
//findById(Integer id)      // ID'ye göre operator bul
//delete(Operator operator)         // Operator sil
//count()                   // Toplam operator sayısı
