package com.example.schimb.service.implementations;


import com.example.schimb.exceptions.users.*;
import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.model.users.Role;
import com.example.schimb.model.users.User;
import com.example.schimb.model.users.UserAudit;
import com.example.schimb.repository.users.RoleRepository;
import com.example.schimb.repository.users.UserRepository;
import com.example.schimb.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void create(UserDTO userDTO) throws UserCreationException, RoleNotFoundException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserCreationException("This email is already used");
        }
        User user = userDTO.toUser();
        user.setRole(getRoleOfUser(userDTO));
        user.setPhoto(userDTO.getPhoto() != null ?
                Base64.getDecoder().decode(userDTO.getPhoto().split(",")[1]) :
                null);

        user = userRepository.save(user);

        Date registrationDate = new Date();

        UserAudit userAudit = UserAudit.builder()
                .email(user.getEmail())
                .telephoneNumber(user.getTelephoneNumber())
                .registrationDate(registrationDate)
                .build();
    }

    @Override
    @Transactional
    public void deleteById(int id)
            throws UserNotFoundException, UserRemovalException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
        if (user.getRole().getName().equals("SYSTEM_ADMINISTRATOR")) {
            throw new UserRemovalException("System administrator can not be deleted");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User getById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new
                UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    @Transactional
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(user -> UserDTO.builder()
                        .age(user.getAge())
                        .role(user.getRole().getName())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .password("**********")
                        .telephoneNumber(user.getTelephoneNumber())
                        .photo(user.getPhoto() != null ?
                                Base64.getEncoder().encodeToString(user.getPhoto()) :
                                null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByEmail(String email)
            throws UserNotFoundException, UserRemovalException {
        User userByEmail = getUserByEmail(email);
        deleteById(userByEmail.getId());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, boolean isPasswordChanged)
            throws RoleNotFoundException {

        User storedUser = getUserByDTO(userDTO);

        storedUser.setRole(getRoleOfUser(userDTO));
        storedUser.setFirstName(userDTO.getFirstName());
        storedUser.setAge(userDTO.getAge());
        storedUser.setLastName(userDTO.getLastName());
        storedUser.setTelephoneNumber(userDTO.getTelephoneNumber());
        storedUser.setPhoto(userDTO.getPhoto() != null ?
                Base64.getDecoder().decode(userDTO.getPhoto().split(",")[1]) :
                null);
        if (isPasswordChanged) {
            storedUser.setPassword(userDTO.getPassword());
        }

    }

    @Override
    @Transactional
    public void changePassword(String email, String oldPassword,
                               String newPassword, String licensePlate,
                               BCryptPasswordEncoder bCryptPasswordEncoder)
            throws UserUpdatingException {

        User user = getUserByEmail(email);

        if (user.getRole().getName().equals("SYSTEM_ADMINISTRATOR")) {
            changeOldPasswordToNew(oldPassword, newPassword, user,
                    bCryptPasswordEncoder);
        } else {
            changeOldPasswordToNew(oldPassword, newPassword, user,
                    bCryptPasswordEncoder);
        }
    }

    private void changeOldPasswordToNew(String oldPassword, String newPassword, User user,
                                        BCryptPasswordEncoder bCryptPasswordEncoder)
            throws UserUpdatingException {

        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        } else {
            throw new UserUpdatingException("Incorrect old password");
        }
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with email " +
                        email + " not found"));
    }

    private User getUserByDTO(UserDTO userDTO) {
        return getUserByEmail(userDTO.getEmail());
    }


    private Role getRoleOfUser(UserDTO userDTO) throws RoleNotFoundException {
        return roleRepository.findByName(userDTO.getRole()).orElseThrow(() ->
                new RoleNotFoundException("Role " + userDTO.getRole() + " does not exist"));
    }
}
