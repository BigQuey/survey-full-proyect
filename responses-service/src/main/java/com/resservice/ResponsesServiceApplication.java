package com.resservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.resservice.feign")
@EnableDiscoveryClient
public class ResponsesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResponsesServiceApplication.class, args);
	}

}
