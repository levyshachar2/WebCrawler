package webcrawler.service.domain.seeds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import webcrawler.service.domain.webpages.WebPagesDAO;

import java.net.URL;

@Repository
public class SeedsDAOImpl implements SeedsDAO {

    private static final Logger logger = LoggerFactory.getLogger(SeedsDAOImpl.class);

    @Autowired
    private WebPagesDAO webPagesDAO;

    @Override
    public boolean addSeed(URL url) {
        return false;
    }
}
