package com.team.servicebooking.repository;

import com.team.servicebooking.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}