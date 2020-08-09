package com.example.schimb.service.implementations;

import com.example.schimb.config.security.JwtTokenUtil;
import com.example.schimb.exceptions.users.InvalidCredentialsException;
import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.model.users.User;
import com.example.schimb.rest.payload.JwtRequest;
import com.example.schimb.rest.payload.JwtResponse;
import com.example.schimb.service.users.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserServiceImpl userService;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public JwtResponse authenticate(JwtRequest request) throws InvalidCredentialsException {
        String email = request.getEmail();
        UserDetails userDetails;
        try {
            userDetails = this.userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String rawPassword = request.getPassword();
        String password = userDetails.getPassword();
        if (bCryptPasswordEncoder.matches(rawPassword, password)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenUtil.generateToken(userDetails);

            User user = userService.getUserByEmail(userDetails.getUsername());
            UserDTO userDTO = UserDTO.builder()
                    .role(user.getRole().getName())
                    .telephoneNumber(user.getTelephoneNumber())
                    .password("**********")
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .email(user.getEmail())
                    .age(user.getAge())
                    .photo(user.getPhoto() != null ?
                            Base64.getEncoder().encodeToString(user.getPhoto()) :
                            null)
                    .build();

            return new JwtResponse(jwtToken, userDTO);
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }
}
