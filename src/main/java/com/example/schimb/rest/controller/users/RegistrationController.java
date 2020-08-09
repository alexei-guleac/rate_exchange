package com.example.schimb.rest.controller.users;

import com.example.schimb.exceptions.users.RoleNotFoundException;
import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.rest.payload.Response;
import com.example.schimb.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RegistrationController {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> addUser(@RequestBody @Valid UserDTO userDTO)
            throws RoleNotFoundException {

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole("USER");

        userService.create(userDTO);

        return ResponseEntity.ok(new Response<>("Successfully Registered"));
    }
}
