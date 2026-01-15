package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.dto.response.MatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {
    Page<MatchResponse> getMatches(Pageable pageable);
}
