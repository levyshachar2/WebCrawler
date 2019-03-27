package webcrawler.service.domain.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.crawler.WebCrawler;
import webcrawler.crawler.WebCrawlerFactory;
import webcrawler.service.domain.Consts;

@Service
public class WebCrawlerRepositoryImpl implements WebCrawlerRepository {

    private final WebCrawlerDAO webCrawlerDAO;
    private final WebCrawlerAsynchronousService webCrawlerAsynchronousService;
    private final WebCrawlerFactory webCrawlerFactory;

    @Autowired
    public WebCrawlerRepositoryImpl(WebCrawlerDAO webCrawlerDAO, WebCrawlerAsynchronousService webCrawlerAsynchronousService, WebCrawlerFactory webCrawlerFactory) {
        this.webCrawlerDAO = webCrawlerDAO;
        this.webCrawlerAsynchronousService = webCrawlerAsynchronousService;
        this.webCrawlerFactory = webCrawlerFactory;
    }


    @Override
    public boolean addWebCrawler(Consts.WebCrawlerType webCrawlerType) {
        WebCrawler webCrawler = webCrawlerFactory.createWebCrawler(webCrawlerType);
        boolean result = webCrawlerDAO.addWebCrawler(webCrawler);
        return webCrawlerAsynchronousService.executeAsynchronously(webCrawler) && result;
    }

    @Override
    public boolean removeWebCrawler(String webCrawlerId) {
        stopWebCrawler(webCrawlerId);
        return webCrawlerDAO.removeWebCrawler(webCrawlerId);
    }

    @Override
    public boolean stopWebCrawler(String webCrawlerId) {
        webCrawlerAsynchronousService.stopWebCrawler(webCrawlerId);
        return true;
    }

    @Override
    public boolean startWebCrawler(String webCrawlerId) {
        return false;
    }
}
