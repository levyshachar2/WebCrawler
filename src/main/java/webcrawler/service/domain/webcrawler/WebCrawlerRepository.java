package webcrawler.service.domain.webcrawler;

import webcrawler.service.domain.Consts;

public interface WebCrawlerRepository {

    boolean addWebCrawler(Consts.WebCrawlerType webCrawlerType);
    boolean removeWebCrawler(String webCrawlerId);
    boolean stopWebCrawler(String webCrawlerId);
    boolean startWebCrawler(String webCrawlerId);
}
