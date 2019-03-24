package webcrawler.service.domain.webpages;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import webcrawler.UrlFrontier.Webpage;
import webcrawler.service.domain.BaseDAOImpl;

import java.util.UUID;

@Repository
public class WebPagesDAOImpl extends BaseDAOImpl implements WebPagesDAO {

    private static final Logger logger = LoggerFactory.getLogger(WebPagesDAOImpl.class);

    private static final String INSERT_WEBPAGE_CLAUSE = "INSERT INTO \"webpage\" (\n" + " \"webpage_id\", \"webpage_url\", \"webpage_searched\",\"webpage_priority\", \"webpage_res_id\" ) \n" + "  VALUES (?,?,?,?,?);";

    private static final String SELECT_WEBPAGE_CLAUSE = "SELECT ";

    private static final String DELETE_WEBPAGE_CLAUSE = "";

    private static final String WHERE_WEBPAGE_ID = "WHERE \"webpage\".\"webpage_id\" = ?";

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
}
