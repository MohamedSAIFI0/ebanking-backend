package net.saifi.ebankingbackend.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message){
        super(message);
    }
}
