package com.team.servicebooking.repository;

import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodStrategyRepository extends JpaRepository<PaymentMethodStrategy, UUID> {

    List<PaymentMethodStrategy> findAllByClient(Client client);
}
