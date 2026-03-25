package com.team.servicebooking.service;

import com.team.servicebooking.model.notification.Notification;
import com.team.servicebooking.model.user.User;
import com.team.servicebooking.repository.NotificationRepository;
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

//        if (configService.getConfiguration().getVerboseNotification()) {
//            System.out.println("[CLIENT NOTIFICATION] " + message);
//        } else {
//            System.out.println(message);
//        }

        //only use this function in order to pass notifications to database.
        //do all checks on whether or not to actually send notification in the calling method

        repo.save( new Notification(user,message) );

    }
}
