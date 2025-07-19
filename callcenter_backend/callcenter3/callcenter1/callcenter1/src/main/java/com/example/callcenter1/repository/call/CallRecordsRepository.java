package com.example.callcenter1.repository.call;

import com.example.callcenter1.model.call.CallRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRecordsRepository extends JpaRepository<CallRecords, Integer> {
}
