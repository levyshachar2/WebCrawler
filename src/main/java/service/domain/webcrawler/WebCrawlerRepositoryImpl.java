package service.domain.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCrawlerRepositoryImpl implements WebCrawlerRepository {

    private final WebCrawlerDAO webCrawlerDAO;

    @Autowired
    public WebCrawlerRepositoryImpl(WebCrawlerDAO webCrawlerDAO) {
        this.webCrawlerDAO = webCrawlerDAO;
    }

    @Override
    public List<WebCrawlerState> getWebCrawlersState() {
        return webCrawlerDAO.getWebCrawlersState();
    }
}
