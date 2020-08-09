package com.example.schimb.service.users;


import com.example.schimb.exceptions.users.RoleNotFoundException;
import com.example.schimb.model.users.Role;


public interface RoleService {
    Role getByName(String name) throws RoleNotFoundException;
}
