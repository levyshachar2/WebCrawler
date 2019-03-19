package service.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.domain.webcrawler.WebCrawlerService;
import service.domain.webcrawler.WebCrawlerState;
import service.handlers.WebCrawlerHandler;

import java.util.List;
@Service
public class WebCrawlerHandlerImpl implements WebCrawlerHandler {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public WebCrawlerHandlerImpl(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    @Override
    public List<WebCrawlerState> getWebCrawlersState() {
        return webCrawlerService.getWebCrawlersState();
    }
}
