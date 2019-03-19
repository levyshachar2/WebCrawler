package service.domain.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final WebCrawlerRepository webCrawlerRepository;

    @Autowired
    public WebCrawlerServiceImpl(WebCrawlerRepository webCrawlerRepository) {
        this.webCrawlerRepository = webCrawlerRepository;
    }

    @Override
    public List<WebCrawlerState> getWebCrawlersState() {
        return null;
    }
}
