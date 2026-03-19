package com.team.servicebooking.repository;

import com.team.servicebooking.config.DatabaseSingleton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfigRepository extends JpaRepository<DatabaseSingleton, UUID> {
}
