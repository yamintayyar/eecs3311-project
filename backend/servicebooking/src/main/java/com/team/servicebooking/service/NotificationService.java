package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyClient(String message) {

        if (DatabaseSingleton.getInstance().getVerboseNotification()) {
            System.out.println("[CLIENT NOTIFICATION] " + message);
        } else {
            System.out.println(message);
        }
    }

    public void notifyConsultant(String message) {

        if (DatabaseSingleton.getInstance().getVerboseNotification()) {
            System.out.println("[CONSULTANT NOTIFICATION] " + message);
        } else {
            System.out.println(message);
        }
    }
}
