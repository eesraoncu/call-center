package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.OperatorCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorCallRepository extends JpaRepository<OperatorCall, Integer> {
    // Gerekirse özel sorgular buraya eklenir
}
