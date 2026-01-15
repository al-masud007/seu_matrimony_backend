package com.matrimony.matrimony_backend.controller;

import com.matrimony.matrimony_backend.dto.response.ApiResponse;
import com.matrimony.matrimony_backend.dto.response.MatchResponse;
import com.matrimony.matrimony_backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MatchResponse>>> getMatches(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Matches fetched successfully", matchService.getMatches(pageable)));
    }
}
