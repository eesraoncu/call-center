package com.example.callcenter1.repository.log;

import com.example.callcenter1.model.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

    @Query("""
        SELECT l FROM Log l
        LEFT JOIN l.operator o
        LEFT JOIN l.customer c
        WHERE 
            (:email IS NULL OR LOWER(o.operatorEmail) LIKE LOWER(CONCAT('%', :email, '%')) 
                             OR LOWER(c.customerEmail) LIKE LOWER(CONCAT('%', :email, '%')))
        AND (:logDescription IS NULL OR LOWER(l.logDescription) LIKE LOWER(CONCAT('%', :logDescription, '%')))
        AND (:method IS NULL OR LOWER(l.method) LIKE LOWER(CONCAT('%', :method, '%')))
        AND (:uri IS NULL OR LOWER(l.uri) LIKE LOWER(CONCAT('%', :uri, '%')))
        AND (:startDate IS NULL OR l.logDatetime >= :startDate)
        AND (:endDate IS NULL OR l.logDatetime <= :endDate)
        AND (:responseStatus IS NULL OR l.responseStatus = :responseStatus)
        ORDER BY l.logDatetime DESC
    """)
    List<Log> filterLogs(
        @Param("email") String email,
        @Param("logDescription") String logDescription,
        @Param("method") String method,
        @Param("uri") String uri,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("responseStatus") Integer responseStatus
    );

    // ✅ Operatöre göre login endpointinde response_body'den "Invalid email or password" içeren hatalı login sayısı
    @Query("""
        SELECT COUNT(l) FROM Log l
        WHERE 
            LOWER(l.uri) = '/login'
            AND l.responseStatus BETWEEN 400 AND 500
            AND LOWER(l.responseBody) LIKE %:errorMessage%
            AND (:operatorId IS NULL OR l.operatorId = :operatorId)
    """)
    long countFailedLoginAttemptsByResponseBody(
        @Param("operatorId") Integer operatorId,
        @Param("errorMessage") String errorMessage
    );

    @Query("""
    SELECT COUNT(l) FROM Log l
    WHERE l.operatorId = :operatorId
      AND l.responseStatus IN :statusList
    """)
    long countFailedLoginAttemptsByOperator(
        @Param("operatorId") Integer operatorId,
        @Param("statusList") List<Integer> statusList
    );
}