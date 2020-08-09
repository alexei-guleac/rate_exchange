package com.example.schimb.service.users;

import com.example.schimb.exceptions.users.RoleNotFoundException;
import com.example.schimb.exceptions.users.UserNotFoundException;
import com.example.schimb.exceptions.users.UserRemovalException;
import com.example.schimb.exceptions.users.UserUpdatingException;
import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.model.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


public interface UserService {
    void create(UserDTO userDTO) throws RoleNotFoundException;

    void deleteById(int id) throws UserNotFoundException, UserRemovalException;

    User getById(int Id) throws UserNotFoundException;

    User getUserByEmail(String email);

    List<UserDTO> getAll();

    void deleteByEmail(String email) throws UserNotFoundException, UserRemovalException;

    void update(UserDTO userDTO, boolean isPasswordChanged) throws UserNotFoundException, RoleNotFoundException, UserUpdatingException;

    void changePassword(String email, String oldPassword, String newPassword, String licensePlate, BCryptPasswordEncoder bCryptPasswordEncoder) throws UserUpdatingException;

}
