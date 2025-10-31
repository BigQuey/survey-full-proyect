package com.surservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.surservice.feign.UserClient;

import feign.FeignException;

@Service
public class FeignUserDetailsService implements UserDetailsService {
    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return userClient.findUserDetailsByEmail(email);
        } catch (FeignException e) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }
    }
}