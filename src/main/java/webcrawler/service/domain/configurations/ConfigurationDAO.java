package webcrawler.service.domain.configurations;

import java.util.UUID;

public interface ConfigurationDAO {

    String getConfiguration(String key, UUID crawlerId);
}
