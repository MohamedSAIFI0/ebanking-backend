package com.ebankingbackend.exceptions;

import com.ebankingbackend.entities.Customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message){
        super(message);
    }
}
