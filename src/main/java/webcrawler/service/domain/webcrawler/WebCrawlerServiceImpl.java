package webcrawler.service.domain.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.service.domain.Consts;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final WebCrawlerRepository webCrawlerRepository;

    @Autowired
    public WebCrawlerServiceImpl(WebCrawlerRepository webCrawlerRepository) {
        this.webCrawlerRepository = webCrawlerRepository;
    }

    @Override
    public boolean addWebCrawler(Consts.WebCrawlerType webCrawlerType) {
        //TODO:  add check if we are currently as MAX web crawlers
        return webCrawlerRepository.addWebCrawler(webCrawlerType);
    }

    @Override
    public boolean removeWebCrawler(String webCrawlerId) {
        return webCrawlerRepository.removeWebCrawler(webCrawlerId);
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
