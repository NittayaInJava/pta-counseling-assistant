package com.example.pta_counseling_assistant.controller;

import com.example.pta_counseling_assistant.dto.CounselingPlanResponse;
import com.example.pta_counseling_assistant.dto.ERezeptRequest;
import com.example.pta_counseling_assistant.service.CounselingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/counseling")
public class CounselingController {

    private final CounselingService counselingService;

    public CounselingController(CounselingService counselingService) {
        this.counselingService = counselingService;
    }

    @PostMapping("/erezept")
    public ResponseEntity<?> generateCounselingPlan(@Valid @RequestBody ERezeptRequest request) {
        try {
            CounselingPlanResponse plan = counselingService.generatePlan(request);
            return ResponseEntity.ok(plan);
        } catch (NoSuchElementException e) {
            // 404 wenn Patient oder Medikament fehlt
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
