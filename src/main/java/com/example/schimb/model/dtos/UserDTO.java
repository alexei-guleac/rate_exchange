package com.example.schimb.model.dtos;

import com.example.schimb.model.users.Role;
import com.example.schimb.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    @NotNull
    @Pattern(regexp = "([a-zA-Z0-9_.+-]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+)")
    private String email;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastName;

    @NotNull
    @Min(18)
    private int age;

    @NotNull
    @Pattern(regexp = "\\+373[0-9]{8}")
    private String telephoneNumber;

    @NotNull
    @Size(min = 2)
    private String password;

    private String company;

    private String role;

    private String photo;

    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .age(age)
                .telephoneNumber(telephoneNumber)
                .firstName(firstName)
                .role(role != null ? Role.builder().name(role).build()
                        : Role.builder().name("USER").build())
                .lastName(lastName)
                .build();
    }
}
