package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.request.InterestRequest;
import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.dto.response.InterestResponse;
import com.matrimony.matrimony_backend.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<ApiResponse<InterestResponse>> sendInterest(@Valid @RequestBody InterestRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Interest sent successfully", interestService.sendInterest(request)));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<ApiResponse<InterestResponse>> acceptInterest(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Interest accepted", interestService.acceptInterest(id)));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<InterestResponse>> rejectInterest(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Interest rejected", interestService.rejectInterest(id)));
    }

    @GetMapping("/sent")
    public ResponseEntity<ApiResponse<List<InterestResponse>>> getSentInterests() {
        return ResponseEntity.ok(ApiResponse.success("Sent interests fetched", interestService.getSentInterests()));
    }

    @GetMapping("/received")
    public ResponseEntity<ApiResponse<List<InterestResponse>>> getReceivedInterests() {
        return ResponseEntity.ok(ApiResponse.success("Received interests fetched", interestService.getReceivedInterests()));
    }
}
