package webcrawler.service.domain.webcrawler;

import webcrawler.crawler.WebCrawler;

public interface WebCrawlerDAO {

    boolean addWebCrawler(WebCrawler webCrawlerType);
    boolean removeWebCrawler(String webCrawlerId);
    boolean stopWebCrawler(String webCrawlerId);
    boolean startWebCrawler(String webCrawlerId);

}
