package webcrawler.service.domain.searchers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import webcrawler.service.domain.BaseDAOImpl;

import java.util.UUID;

@Repository
public class SearcherDAOImpl extends BaseDAOImpl implements SearcherDAO {
    private static final Logger logger = LoggerFactory.getLogger(SearcherDAOImpl.class);


    private final static String INSERT_SERACHER_CLAUSE =  "INSERT INTO \"searcher\" (\n" + " \"searcher_id\" ) \n" + "  VALUES (?);";

    @Override
    public boolean addSearcher(UUID searcherId) {
        Assert.notNull(searcherId, "searcherId cannot be null");
        try {
            int affectedRows = getJdbc().update(INSERT_SERACHER_CLAUSE,
                    searcherId) ;
            return affectedRows == 1;
        } catch (DataAccessException ex) {
            logger.error("Failed to add url in DB", ex);
            throw ex;
        }
    }
}
