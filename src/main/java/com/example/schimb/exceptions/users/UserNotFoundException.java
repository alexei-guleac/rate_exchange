package com.example.schimb.exceptions.users;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
