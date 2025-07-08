package com.example.callcenter1.service;

import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.repository.log.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public List<Log> getAllLogs() { return logRepository.findAll(); }
    public Log saveLog(Log log) { return logRepository.save(log); }
    public void deleteLog(Integer id) { logRepository.deleteById(id); }
}
