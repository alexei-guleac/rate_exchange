package com.example.schimb.service.users;


import com.example.schimb.exceptions.users.InvalidCredentialsException;
import com.example.schimb.rest.payload.JwtRequest;
import com.example.schimb.rest.payload.JwtResponse;


public interface AuthenticationService {
    JwtResponse authenticate(JwtRequest request) throws InvalidCredentialsException;
}
