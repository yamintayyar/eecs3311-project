package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.repository.ConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;

        //TODO: we need to have a config available by default. add default configuration here
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
        config.setVerboseNotifications(verboseNotifications);
        configRepository.save(config);
    }

    @Transactional
    public DatabaseSingleton getConfiguration() {
        return null; //TODO: return most recent row in config database. likely will need a simple SQL query
    }

}
