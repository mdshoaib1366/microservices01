package com.nit.mybank.mapper;

import com.nit.mybank.dto.CustomerRequest;
import com.nit.mybank.dto.CustomerSummeryDTO;
import com.nit.mybank.entity.Customer;

public class CustomerMapper {
    public static Customer dtoToEntity(CustomerSummeryDTO customerDto) {
        return Customer.builder()
                .email(customerDto.getCustomerEmail())
                .name(customerDto.getCustomerName())
                .id(customerDto.getCustomerId())
                .build();
    }

    public static CustomerSummeryDTO entityToDto(Customer customer) {
        return CustomerSummeryDTO.builder()
                .customerEmail(customer.getEmail())
                .customerName(customer.getName())
                .customerId(customer.getId())
                .build();
    }

    public static Customer toEntity(CustomerRequest reqCustomer) {
        return Customer.builder()
                .email(reqCustomer.getEmail())
                .name(reqCustomer.getName())
                .password(reqCustomer.getPassword())
                .build();
    }
}
