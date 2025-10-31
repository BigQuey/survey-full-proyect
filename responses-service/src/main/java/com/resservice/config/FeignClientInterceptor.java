package com.resservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import feign.RequestInterceptor;
import feign.RequestTemplate;


@Configuration
public class FeignClientInterceptor {
	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null && authentication.getCredentials() instanceof String jwt) {
                    requestTemplate.header(AUTHORIZATION_HEADER, "Bearer " + jwt);
                }
            }
        };
    }
}
