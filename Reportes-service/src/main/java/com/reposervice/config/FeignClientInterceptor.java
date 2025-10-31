package com.reposervice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {
	

	@Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authorization = request.getHeader("Authorization");

            if (authorization != null) {
                template.header("Authorization", authorization);
            }
        }
    }
}
