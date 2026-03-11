package com.team.servicebooking.repository;

import com.team.servicebooking.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}