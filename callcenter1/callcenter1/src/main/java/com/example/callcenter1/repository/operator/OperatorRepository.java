package com.example.callcenter1.repository.operator;

import com.example.callcenter1.model.operator.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {
    Optional<Operator> findByOperatorName(String operatorName);
    Optional<Operator> findByOperatorEmail(String operatorEmail);
    boolean existsByOperatorEmail(String email);
}


// JpaRepository'den miras aldığınız için aşağıdaki metotlar zaten mevcuttur ve manuel yazmanıza gerek yoktur:
// - save(Operator operator)           // Operator kaydet/güncelle
// - findAll()                        // Tüm operatorleri getir
// - findById(Integer id)             // ID'ye göre operator bul (Bu metot zaten Optional döner)
// - delete(Operator operator)         // Operator sil
// - count()                          // Toplam operator sayısı

/**
 * Optional<Operator> findByOperatorName(String operatorName); // Tek bir sonuç beklendiği için Optional
 * Operator'ı adına göre bulur.
 * Eğer operator adı benzersiz ise ve tek bir sonuç bekleniyorsa, Optional dönmek iyi bir yaklaşımdır.
 * Operator isimleri benzersiz olmayabilir, bu durumda List<Operator> dönmek daha uygun olabilir.
 * Ancak tek bir operatör adının beklendiği senaryolar için Optional uygundur.
 */

/**
 * Optional<Operator> findByOperatorEmail(String operatorEmail); [cite_start]// Tek ve benzersiz sonuç beklendiği için Optional [cite: 37, 38]
 * Operator'ı e-posta adresine göre bulur. Genellikle login işlemleri için kullanılır.
 * E-posta adreslerinin benzersiz olması beklendiği için Optional dönmek idealdir.
 */

/**
 * Optional<Operator> findByOperatorPhoneNumber(String operatorPhoneNumber); [cite_start]// Tek ve benzersiz sonuç beklendiği için Optional [cite: 37, 38]
 * Operator'ı telefon numarasına göre bulur.
 * Telefon numaralarının benzersiz olması bekleniyorsa Optional, birden fazla olabilecekse List<Operator> uygun olur.
 */
// Örnek: Ad ve soyada göre operatör bulma
// List<Operator> findByOperatorNameAndOperatorSurname(String operatorName, String operatorSurname);

// Örnek: Adında belirli bir kelime geçen operatörleri bulma
// List<Operator> findByOperatorNameContaining(String keyword);