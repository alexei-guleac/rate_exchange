package com.example.schimb.rest.controller.users;

import com.example.schimb.config.security.JwtTokenUtil;
import com.example.schimb.exceptions.users.RoleNotFoundException;
import com.example.schimb.exceptions.users.UserNotFoundException;
import com.example.schimb.exceptions.users.UserRemovalException;
import com.example.schimb.exceptions.users.UserUpdatingException;
import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.model.users.User;
import com.example.schimb.service.users.RoleService;
import com.example.schimb.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO)
            throws RoleNotFoundException {

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userService.create(userDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/update")
    @Transactional
    public ResponseEntity<?> updateUser(@RequestParam boolean isPasswordChanged,
                                        @RequestBody @Valid UserDTO userDTO,
                                        HttpServletRequest httpServletRequest)
            throws UserNotFoundException, RoleNotFoundException, UserUpdatingException {

        final String requestTokenHeader = httpServletRequest
                .getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        User requestingUser = userService.getUserByEmail(email);
        if (requestingUser.getRole() == roleService.getByName("SYSTEM_ADMINISTRATOR")) {
            User userToUpdate = userService.getUserByEmail(userDTO.getEmail());
            if (userToUpdate.getRole() == roleService.getByName("SYSTEM_ADMINISTRATOR") &&
                    !requestingUser.getEmail().equals(userDTO.getEmail())) {
                throw new UserUpdatingException("System Administrator can not update " +
                        "another System Administrator");
            }
        } else {
            if (!email.equals(userDTO.getEmail()) ||
                    roleService.getByName(userDTO.getRole()) != requestingUser.getRole()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }

        if (isPasswordChanged) {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userService.update(userDTO, isPasswordChanged);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> deleteUserById(@PathVariable int id)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<?> deleteUserByEmail(
            @RequestParam(name = "email") String email)
            throws UserNotFoundException, UserRemovalException {

        userService.deleteByEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<User> findOneUser(@PathVariable int id)
            throws UserNotFoundException {

        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword,
                                            @RequestParam String newPassword,
                                            @RequestParam(required = false)
                                                    String licensePlate,
                                            HttpServletRequest httpServletRequest)
            throws UserUpdatingException {

        final String requestTokenHeader = httpServletRequest
                .getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String email = jwtTokenUtil.getEmailFromToken(jwtToken);

        userService.changePassword(email, oldPassword,
                newPassword, licensePlate, bCryptPasswordEncoder);

        return ResponseEntity.noContent().build();
    }
}
