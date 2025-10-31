package com.reposervice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "responses-service")
public interface ResponseClient {
    
    @GetMapping("/api/responses/count")
    long countResponses();
}