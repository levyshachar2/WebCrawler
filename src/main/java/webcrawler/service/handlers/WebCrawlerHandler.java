package webcrawler.service.handlers;

import webcrawler.service.domain.Consts;
import webcrawler.service.domain.webcrawler.WebCrawlerState;

import java.util.List;

public interface WebCrawlerHandler {

    boolean addWebCrawler(Consts.WebCrawlerType webCrawlerType);
    boolean removeWebCrawler(String webCrawlerId);
    boolean stopWebCrawler(String webCrawlerId);
    boolean startWebCrawler(String webCrawlerId);

}
