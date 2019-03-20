package webcrawler.service.domain.webcrawler;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebCrawlerDAOImpl implements WebCrawlerDAO {

    @Override
    public List<WebCrawlerState> getWebCrawlersState() {
        return null;
    }
}
