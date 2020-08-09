package com.example.schimb.rest.controller.users;


import com.example.schimb.rest.payload.JwtRequest;
import com.example.schimb.rest.payload.JwtResponse;
import com.example.schimb.service.users.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
