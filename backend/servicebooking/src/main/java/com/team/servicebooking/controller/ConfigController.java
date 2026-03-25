package com.team.servicebooking.controller;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.service.ConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

//    private final DatabaseSingleton config = DatabaseSingleton.getInstance(); //TODO: See TODOs in ConfigService and DatabaseSingleton
//
    private final ConfigService config;

    ConfigController(ConfigService config) {
        this.config = config;
    }

    @GetMapping
    public DatabaseSingleton getConfig() {
        return config.getConfiguration();
    }

    @PostMapping("/discount/{value}")
    public void setDiscount(@PathVariable double value) {
        config.setDiscount(value);
    }

    @PostMapping("/min-notice/{hours}")
    public void setMinNotice(@PathVariable int hours) {
        config.setCancellationDeadline(hours);
    }

    @PostMapping("/switch-verbose/{setting}")
    public void switchVerbose(@PathVariable boolean setting) {
        config.setVerboseNotifications(setting);
    }

}
