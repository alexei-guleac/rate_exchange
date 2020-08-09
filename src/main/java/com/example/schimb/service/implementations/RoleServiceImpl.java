package com.example.schimb.service.implementations;

import com.example.schimb.exceptions.users.RoleNotFoundException;
import com.example.schimb.model.users.Role;
import com.example.schimb.repository.users.RoleRepository;
import com.example.schimb.service.users.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) throws RoleNotFoundException {
        return roleRepository.findByName(name).orElseThrow(() ->
                new RoleNotFoundException("This role doesn't exist"));
    }
}
