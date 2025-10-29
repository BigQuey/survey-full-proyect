package com.reposervice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "responses-service")
public interface ResponseClient {
    // Necesitamos un endpoint en Response-service que devuelva el conteo total de respuestas.
    @GetMapping("/api/responses/count")
    long countResponses();
}