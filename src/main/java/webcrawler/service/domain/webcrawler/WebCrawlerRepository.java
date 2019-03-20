package webcrawler.service.domain.webcrawler;

import java.util.List;

public interface WebCrawlerRepository {

    List<WebCrawlerState> getWebCrawlersState();
}
