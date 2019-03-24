package webcrawler.service.domain.webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import webcrawler.crawler.WebCrawler;
import webcrawler.service.domain.BaseDAOImpl;

@Repository
public class WebCrawlerDAOImpl extends BaseDAOImpl implements WebCrawlerDAO {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawlerDAOImpl.class);


    private static final String INSERT_WEBCRAWLER_CLAUSE = "INSERT INTO \"web_crawler\" (\n" + " \"web_crawler_id\", \"web_crawler_type\") \n" + "  VALUES (?,?);";

    private static final String DELETE_WEBCRAWLER_CLAUSE = "DELETE FROM \"web_crawler\" \n";
    private static final String WHERE_WEBCRAWLER_ID = "WHERE \"web_crawler_id\" = ? \n";

    @Override
    public boolean addWebCrawler(WebCrawler webCrawler) {
        Assert.notNull(webCrawler, "WebCrawler must not be null");
        try {
            int affectedRows = getJdbc().update(INSERT_WEBCRAWLER_CLAUSE, webCrawler.getId(), webCrawler.getWebCrawlerType().getValue());
            return affectedRows == 1;
        } catch (DataAccessException ex) {
            logger.error("Failed to create order in DB", ex);
            throw ex;
        }
    }

    @Override
    public boolean removeWebCrawler(String webCrawlerId) {
        Assert.notNull(webCrawlerId, "webCrawlerId must not be null");
        try {
            String DELETE_WHERE = DELETE_WEBCRAWLER_CLAUSE + WHERE_WEBCRAWLER_ID;
            int affectedRows = getJdbc().update(DELETE_WHERE, webCrawlerId);
            return affectedRows == 1;
        } catch (DataAccessException ex) {
            logger.error("Failed to create order in DB", ex);
            throw ex;
        }
    }



    @Override
    public boolean stopWebCrawler(String webCrawlerId) {
        return false;
    }

    @Override
    public boolean startWebCrawler(String webCrawlerId) {
        return false;
    }
}
