package webcrawler.service.domain.webpages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import webcrawler.UrlFrontier.Webpage;
import webcrawler.service.domain.BaseDAOImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class WebPagesDAOImpl extends BaseDAOImpl implements WebPagesDAO {

    private static final Logger logger = LoggerFactory.getLogger(WebPagesDAOImpl.class);

    private static final String INSERT_WEBPAGE_CLAUSE = "INSERT INTO \"webpage\" (\n" + " \"webpage_id\", \"webpage_url\", \"webpage_searched\",\"webpage_priority\", \"webpage_res_id\" ) \n" + "  VALUES (?,?,?,?,?)";

    private static final String SELECT_WEBPAGE_CLAUSE = "SELECT * FROM \"webpage\"";

    private static final String WHERE_SEARCHED = " WHERE \"webpage_searched\" = :IS_SEARCHED";

    private static final String DELETE_WEBPAGE_CLAUSE = "";

    private static final String WHERE_WEBPAGE_ID = "WHERE \"webpage\".\"webpage_id\" = ?";

    private static final String INSERT_WEBPAGES_UPCERT = "INSERT INTO \"webpage\" (\"webpage_id\", \"webpage_url\", \"webpage_searched\", \"webpage_priority\" , \"webpage_res_id\" ) VALUES(?,?,?,?,?)\n" +
            "ON CONFLICT ON CONSTRAINT \"webpage_unique_url\" DO NOTHING";


    private final WebpageRowMapper webpageRowMapper;

    @Autowired
    public WebPagesDAOImpl(WebpageRowMapper webpageRowMapper) {
        this.webpageRowMapper = webpageRowMapper;
    }

    @Override
    public boolean addWebpage(Webpage webpage) {
        Assert.notNull(webpage, "Webpage cannot be null");
        try {
            int affectedRows = getJdbc().update(INSERT_WEBPAGE_CLAUSE,
                    webpage.getUrlId(),webpage.getURL().toString(),
                    webpage.isSearched(),webpage.getTimesVisited(), null) ;
            return affectedRows == 1;
        } catch (DataAccessException ex) {
            logger.error("Failed to add url in DB", ex);
            throw ex;
        }
    }

    @Override
    public Webpage getWebPage(UUID webpageId) {
        return null;
    }

    @Override
    public boolean addAllWebpages(List<Webpage> webpageList) {
        Assert.notNull(webpageList, "Webpage list cannot be null");
        try {
            getJdbc().batchUpdate(INSERT_WEBPAGES_UPCERT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Webpage current = webpageList.get(i);
                    if (current.getURL().toString().length() > 255) {
                        return;
                    }
                    ps.setObject(1, current.getUrlId());
                    ps.setString(2, current.getURL().toString());
                    ps.setBoolean(3, current.isSearched());
                    ps.setInt(4, current.getTimesVisited());
                    ps.setObject(5, null);
                }

                @Override
                public int getBatchSize() {
                    return webpageList.size();
                }
            });

            return true;
        } catch (DataAccessException ex) {
            logger.error("Failed to add url in DB", ex);
            throw ex;
        }
    }

    @Override
    public List<Webpage> getAllWebpages(boolean searched) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("IS_SEARCHED",searched);
            String sql = SELECT_WEBPAGE_CLAUSE + WHERE_SEARCHED;
            return (new NamedParameterJdbcTemplate(getJdbc()).query(sql,mapSqlParameterSource,webpageRowMapper));
        } catch (DataAccessException e) {
            logger.error("Failed to getAllWebpages DB", e);
            throw e;
        }
    }
}
