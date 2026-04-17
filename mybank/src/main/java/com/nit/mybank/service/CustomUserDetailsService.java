package com.nit.mybank.service;

import com.nit.mybank.entity.Customer;
import com.nit.mybank.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getEmail())
                .password(customer.getPassword())
                .authorities(grantedAuthority)
                .build();
    }
}