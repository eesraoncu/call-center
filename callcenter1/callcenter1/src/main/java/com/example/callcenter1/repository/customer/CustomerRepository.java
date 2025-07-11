package com.example.callcenter1.repository.customer;
import com.example.callcenter1.model.customer.Customer; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //Optional<Customer> findByCustomerEmail(String email);
    //Optional<Customer> findByCustomerPhoneNumber(String phoneNumber);
}

