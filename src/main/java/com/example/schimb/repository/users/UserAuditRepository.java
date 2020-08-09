package com.example.schimb.repository.users;

import com.example.schimb.model.users.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
}
