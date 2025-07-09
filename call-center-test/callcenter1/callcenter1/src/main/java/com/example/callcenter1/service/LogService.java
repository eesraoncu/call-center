package com.example.callcenter1.service;

import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.repository.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public List<Log> findAll() {
        return logRepository.findAll();
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
}
