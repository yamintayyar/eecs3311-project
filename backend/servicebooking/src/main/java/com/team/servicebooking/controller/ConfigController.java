package com.team.servicebooking.controller;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.service.ConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final ConfigService config;

    public ConfigController(ConfigService config) {
        this.config = config;
    }

    // GET ALL CONFIG
    @GetMapping
    public DatabaseSingleton getConfig() {
        return config.getConfiguration();
    }

    // UPDATE DISCOUNT
    @PutMapping("/discount/{value}")
    public void setDiscount(@PathVariable double value) {
        config.setDiscount(value);
    }

    // UPDATE MIN NOTICE
    @PutMapping("/min-notice/{hours}")
    public void setMinNotice(@PathVariable int hours) {
        config.setCancellationDeadline(hours);
    }

    // UPDATE VERBOSE
    @PutMapping("/verbose/{setting}")
    public void setVerbose(@PathVariable boolean setting) {
        config.setVerboseNotifications(setting);
    }

    // UPDATE REFUND POLICY
    @PutMapping("/refund/{enabled}")
    public void setRefund(@PathVariable boolean enabled) {
        config.setRefundPolicy(enabled);
    }
}