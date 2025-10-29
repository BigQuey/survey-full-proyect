package com.surservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

//@Configuration
public class FeignClientInterceptor {
	private static final String AUTHORIZATION_HEADER = "Authorization";

 //   @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // Obtenemos la petición HTTP actual
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    
                    // Obtenemos la cabecera 'Authorization' de la petición original
                    String authHeader = request.getHeader(AUTHORIZATION_HEADER);
                    
                    // Si la cabecera existe, la añadimos a la nueva petición de Feign
                    if (authHeader != null && !authHeader.isEmpty()) {
                        requestTemplate.header(AUTHORIZATION_HEADER, authHeader);
                    }
                }
            }
        };
    }
}
