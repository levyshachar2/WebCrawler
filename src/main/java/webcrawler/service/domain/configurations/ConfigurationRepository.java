package webcrawler.service.domain.configurations;

import java.util.UUID;

public interface ConfigurationRepository {

    String getConfiguration(String key, UUID crawlerId);
}
