package com.team.servicebooking.service;

import com.team.servicebooking.model.notification.Notification;
import com.team.servicebooking.model.user.User;
import com.team.servicebooking.repository.NotificationRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final ConfigService configService;
    private final NotificationRepository repo;

    public NotificationService(ConfigService configService, NotificationRepository repo) {
        this.configService = configService;
        this.repo = repo;
    }

    public void notify(User user, String message) {
        if (user == null)
            return;
        boolean verbosity = configService.getConfiguration().getVerboseNotification();
        LocalDateTime createdAt = LocalDateTime.now();
        String finalMessage = "";

        if (verbosity) {
            finalMessage = "[ NOTIFICATION FOR " + user.getName() + " ]. " + message
                    + ". This is an automated verbose message from one of the admins. Sent at" + createdAt.toString()
                    + ". Have a nice day! :)";
        } else {
            finalMessage = message;
        }

        repo.save(new Notification(user, finalMessage));

    }
}
