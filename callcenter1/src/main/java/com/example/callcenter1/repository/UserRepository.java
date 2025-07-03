package com.example.callcenter1.repository;

import com.example.callcenter1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName); //findByUserName fonksiyonu, user.java içindeki userName ile yapmak istediğimiz şartlı sql sorgusunu oluşturur.
} 

//save(User user)           // Kullanıcı kaydet/güncelle
//findAll()                 // Tüm kullanıcıları getir
//findById(Integer id)      // ID'ye göre kullanıcı bul
//delete(User user)         // Kullanıcı sil
//count()                   // Toplam kullanıcı sayısı
