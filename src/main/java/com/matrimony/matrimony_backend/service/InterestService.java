package com.matrimony.matrimony_backend.service;

import com.matrimony.matrimony_backend.dto.request.InterestRequest;
import com.matrimony.matrimony_backend.dto.response.InterestResponse;

import java.util.List;
import java.util.UUID;

public interface InterestService {
    InterestResponse sendInterest(InterestRequest request);
    InterestResponse acceptInterest(UUID interestId);
    InterestResponse rejectInterest(UUID interestId);
    List<InterestResponse> getSentInterests();
    List<InterestResponse> getReceivedInterests();
}
