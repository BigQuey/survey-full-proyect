package com.reposervice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "survey-service")
public interface SurveyClient {
    // Necesitamos un endpoint en Survey-service que devuelva el conteo total de encuestas.
    @GetMapping("/api/surveys/count")
    long countSurveys();
}
