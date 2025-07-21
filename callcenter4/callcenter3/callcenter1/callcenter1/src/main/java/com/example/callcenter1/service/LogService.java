package com.example.callcenter1.service;

import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.repository.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> filterLogs(String email, String logDescription, String method, String uri, LocalDateTime startDate, LocalDateTime endDate, Integer responseStatus) {
        return logRepository.filterLogs(email, logDescription, method, uri, startDate, endDate, responseStatus);
    }

    // ✅ Hatalı login sayısını verir (response_body'deki mesaj üzerinden)
    public long getFailedLoginCountByOperator(Integer operatorId) {
        return logRepository.countFailedLoginAttemptsByResponseBody(operatorId, "invalid email or password");
    }

    public long getFailedLoginAttemptsByResponseBody(Integer operatorId, String errorMessage) {
        return logRepository.countFailedLoginAttemptsByResponseBody(operatorId, errorMessage.toLowerCase());
    }

    public List<Log> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("logDatetime").descending());
        return logRepository.findAll(pageable).getContent(); // ✅ Sadece belirli sayıda veri çeker
    }

    public Log findById(Integer id) {
        Optional<Log> log = logRepository.findById(id);
        return log.orElse(null);
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }

    public Log update(Integer id, Log log) {
        if (logRepository.existsById(id)) {
            log.setLogId(id);
            return logRepository.save(log);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (logRepository.existsById(id)) {
            logRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Log createLog(Log log) {
        log.setLogDatetime(LocalDateTime.now());
        return logRepository.save(log);
    }
    
    public long getTotalFailedLoginCount(Integer operatorId) {
        List<Integer> statusList = Arrays.asList(400, 401, 403);
        return logRepository.countFailedLoginAttemptsByOperator(operatorId, statusList);
    }
}