package com.example.callcenter1.controller;

import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calls")
public class CallController {
    @Autowired
    private CallService callService;

    @GetMapping
    public List<CallRecords> getAllCalls() {
        return callService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallRecords> getCallById(@PathVariable Integer id) {
        CallRecords call = callService.findById(id);
        return call != null ? ResponseEntity.ok(call) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CallRecords createCall(@RequestBody CallRecords call) {
        return callService.save(call);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CallRecords> updateCall(@PathVariable Integer id, @RequestBody CallRecords call) {
        CallRecords updated = callService.update(id, call);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCall(@PathVariable Integer id) {
        boolean deleted = callService.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
