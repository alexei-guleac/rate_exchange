package com.example.schimb;

import com.example.schimb.model.dtos.UserDTO;
import com.example.schimb.model.users.Role;
import com.example.schimb.repository.users.RoleRepository;
import com.example.schimb.service.users.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication()
@ComponentScan(basePackages = {"com.example.schimb.*"})
@EnableJpaRepositories
@PropertySources({@PropertySource("classpath:application.properties"),
        // database configuration file
        @PropertySource("classpath:postgresql-config.properties"),
        // documentation
        @PropertySource("classpath:documentation.properties")})
public class SchimbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchimbApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(RoleRepository roleRepository,
                                  UserService userService,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {

        return args -> {
            Role userRole = Role.builder()
                    .name("USER")
                    .build();
            Role adminRole = Role.builder()
                    .name("SYSTEM_ADMINISTRATOR")
                    .build();
            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            UserDTO userDTO = UserDTO.builder()
                    .age(23)
                    .company("Sample")
                    .email("sample_user@gmail.com")
                    .firstName("alexei")
                    .lastName("g")
                    .password("admin")
                    .telephoneNumber("+373000000")
                    .role("SYSTEM_ADMINISTRATOR")
                    .build();
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

            userService.create(userDTO);
        };
    }
}
