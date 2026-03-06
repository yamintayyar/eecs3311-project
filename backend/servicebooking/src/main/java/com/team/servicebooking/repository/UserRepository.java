package com.team.servicebooking.repository;

import com.team.servicebooking.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}