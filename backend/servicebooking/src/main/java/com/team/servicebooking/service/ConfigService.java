package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.repository.ConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.util.Optional;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @PostConstruct // ← runs after bean is initialized and DB is ready
    public void seedDefaultConfig() {
        if (configRepository.findFirstByOrderByCreatedAtDesc().isEmpty()) {
            DatabaseSingleton defaultConfig = new DatabaseSingleton();
            configRepository.save(defaultConfig);
        }
    }

    @Transactional
    public void setCancellationDeadline(int hours_prior) {
        DatabaseSingleton config = this.getConfiguration();
        config.setMinNotice(hours_prior);
        configRepository.save(config);
    }

    @Transactional
    public void setDiscount(double discount) {
        DatabaseSingleton config = this.getConfiguration();
        config.setDiscount(discount);
        configRepository.save(config);
    }

    @Transactional
    public void setRefundPolicy(boolean refund_allowed) {
        DatabaseSingleton config = this.getConfiguration();
        config.setRefundPolicy(refund_allowed);
        configRepository.save(config);
    }

    @Transactional
    public void setVerboseNotifications(boolean verboseNotifications) {
        DatabaseSingleton config = this.getConfiguration();
        config.setVerboseNotification(verboseNotifications);
        configRepository.save(config);
    }

    @Transactional
    public DatabaseSingleton getConfiguration() {
        Optional<DatabaseSingleton> cfg = configRepository.findFirstByOrderByCreatedAtDesc();

        if (cfg.isEmpty()) {
            DatabaseSingleton new_cfg = new DatabaseSingleton();
            configRepository.save(new_cfg);
            return new_cfg;
        }

        return cfg.get();
    }
}