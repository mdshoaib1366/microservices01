package com.nit.mybank.mapper;

import com.nit.mybank.dto.AccountResponse;
import com.nit.mybank.dto.CustomerSummeryDTO;
import com.nit.mybank.entity.Account;
import com.nit.mybank.entity.Customer;

public class AccountMapper {

    public static AccountResponse toDto(Account account) {
        if (account == null) {
            return null;
        }

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .version(account.getVersion())
                .createdAt(account.getCreatedAt())
                .customerDto(mapCustomer(account.getCustomer()))
                .build();
    }

    private static CustomerSummeryDTO mapCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerSummeryDTO.builder()
                .customerId(customer.getId())
                .customerName(customer.getName())
                .customerEmail(customer.getEmail())
                .build();
    }
}