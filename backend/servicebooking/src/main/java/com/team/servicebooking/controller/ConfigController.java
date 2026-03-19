package com.team.servicebooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.servicebooking.config.DatabaseSingleton;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final DatabaseSingleton config = DatabaseSingleton.getInstance(); //TODO: See TODOs in ConfigService and DatabaseSingleton

    @GetMapping
    public DatabaseSingleton getConfig() {
        return config;
    }

    @PostMapping("/discount/{value}")
    public void setDiscount(@PathVariable double value) {
        config.setDiscount(value);
    }

    @PostMapping("/min-notice/{hours}")
    public void setMinNotice(@PathVariable int hours) {
        config.setMinNotice(hours);
    }

    @PostMapping("/switch-verbose/{setting}")
    public void switchVerbose(@PathVariable boolean setting) {
        config.setVerboseNotifications(setting);
    }

}
