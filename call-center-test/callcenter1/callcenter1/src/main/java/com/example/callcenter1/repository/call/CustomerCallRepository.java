package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CustomerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCallRepository extends JpaRepository<CustomerCall, Integer> {
    // Gerekirse özel sorgular buraya eklenir
}
