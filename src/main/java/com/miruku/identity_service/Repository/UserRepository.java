package com.miruku.identity_service.Repository;

import com.miruku.identity_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
