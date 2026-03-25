package com.team.servicebooking.repository;

import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentMethodStrategyRepository extends JpaRepository<PaymentMethodStrategy, UUID> {

}
