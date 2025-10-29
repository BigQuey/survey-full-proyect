package com.surservice.feign;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "responses-service")
public interface ResponseClient {
    @GetMapping("/api/responses/my-completed-ids")
    Set<Long> getMyCompletedSurveyIds(); 

}