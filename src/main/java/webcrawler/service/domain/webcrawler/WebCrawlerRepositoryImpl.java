package webcrawler.service.domain.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.crawler.WebCrawler;
import webcrawler.crawler.WebCrawlerFactory;
import webcrawler.service.AsynchronousService;
import webcrawler.service.domain.Consts;

@Service
public class WebCrawlerRepositoryImpl implements WebCrawlerRepository {

    private final WebCrawlerDAO webCrawlerDAO;
    private final AsynchronousService asynchronousService;
    private final WebCrawlerFactory webCrawlerFactory;

    @Autowired
    public WebCrawlerRepositoryImpl(WebCrawlerDAO webCrawlerDAO, AsynchronousService asynchronousService, WebCrawlerFactory webCrawlerFactory) {
        this.webCrawlerDAO = webCrawlerDAO;
        this.asynchronousService = asynchronousService;
        this.webCrawlerFactory = webCrawlerFactory;
    }


    @Override
    public boolean addWebCrawler(Consts.WebCrawlerType webCrawlerType) {
        WebCrawler webCrawler = webCrawlerFactory.createWebCrawler(webCrawlerType);
        boolean result = webCrawlerDAO.addWebCrawler(webCrawler);
        return asynchronousService.executeAsynchronously(webCrawler) && result;
    }

    @Override
    public boolean removeWebCrawler(String webCrawlerId) {
        stopWebCrawler(webCrawlerId);
        return webCrawlerDAO.removeWebCrawler(webCrawlerId);
    }

    @Override
    public boolean stopWebCrawler(String webCrawlerId) {
        asynchronousService.stopWebCrawler(webCrawlerId);
        return true;
    }

    @Override
    public boolean startWebCrawler(String webCrawlerId) {
        return false;
    }
}
