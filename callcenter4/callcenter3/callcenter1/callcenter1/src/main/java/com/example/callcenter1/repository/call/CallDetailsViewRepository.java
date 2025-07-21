package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CallDetailsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDetailsViewRepository extends JpaRepository<CallDetailsView, Integer> {
    // Ekstra sorgular ekleyebilirsiniz
} 