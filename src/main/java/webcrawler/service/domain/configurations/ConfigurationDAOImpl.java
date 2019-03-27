package webcrawler.service.domain.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import webcrawler.service.domain.BaseDAOImpl;

import java.util.UUID;

@Repository
public class ConfigurationDAOImpl extends BaseDAOImpl implements ConfigurationDAO {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationDAOImpl.class);


    private static final String SELECT_CLAUSE = "SELECT \"con_value\" FROM \"configurations\"";
    private static final String WHERE_WEBCRAWLER_ID = " AND \"con_crawler_id\" = :con_crawler_id";
    private static final String WHERE_KEY = " WHERE \"con_key\" = :con_key";


    @Override
    public String getConfiguration(String key, UUID crawlerId) {
        Assert.notNull(key, "key must not be null");
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("con_key",key);
            String whereClause = WHERE_KEY;
            if (crawlerId != null){
                whereClause += WHERE_WEBCRAWLER_ID;
                parameters.addValue("con_crawler_id",crawlerId);
            }

            String sql = SELECT_CLAUSE + whereClause;
            String configurationValue = (new NamedParameterJdbcTemplate(getJdbc())).queryForObject(sql,parameters, String.class);
            return configurationValue;
        } catch (DataAccessException e){
            logger.error("failed to get configuration value using key "+ key);
            throw e;
        }



    }
}
