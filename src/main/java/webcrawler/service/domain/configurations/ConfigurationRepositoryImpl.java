package webcrawler.service.domain.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfigurationRepositoryImpl implements ConfigurationRepository {

    private final ConfigurationDAO configurationDAO;

    @Autowired
    public ConfigurationRepositoryImpl(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
    }

    @Override
    public String getConfiguration(String key, UUID crawlerId) {
        return configurationDAO.getConfiguration(key, crawlerId);
    }
}
