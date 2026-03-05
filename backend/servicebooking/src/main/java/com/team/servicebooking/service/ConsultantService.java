package com.team.servicebooking.service;

import java.util.*;


import org.springframework.stereotype.Service;
import com.team.servicebooking.model.user.Consultant;

@Service
public class ConsultantService {
    
    private final Map<UUID, Consultant> consultants = new HashMap<>();

    public Consultant createConsultant(String name, String email, String password) {
        UUID id = UUID.randomUUID();
        Consultant consultant = new Consultant(id, name, email, password);
        consultants.put(id, consultant);
        return consultant;
    }

    public List<Consultant> getAllConsultants() {
        return new ArrayList<>(consultants.values());
    }

    public Optional<Consultant> getConsultantById(UUID id) {
        return Optional.ofNullable(consultants.get(id));
    }
}

