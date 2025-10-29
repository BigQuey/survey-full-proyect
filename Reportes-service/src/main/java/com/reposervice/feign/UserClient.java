package com.reposervice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "users-service")
public interface UserClient {
	@GetMapping("/api/users/count")
    long countUsers();
}
