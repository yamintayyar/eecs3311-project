package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.repository.ConfigRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    @Query(value = "SELECT * FROM config ORDER BY ", nativeQuery = true)
    public DatabaseSingleton getConfiguration() {
        Optional<DatabaseSingleton> cfg =  configRepository.findFirstByOrderByCreatedAtDesc();

        if (cfg.isEmpty()) {
            DatabaseSingleton new_cfg = new DatabaseSingleton();
            configRepository.save(new_cfg);
            return new_cfg;
        }

        return cfg.get();

    }

}
