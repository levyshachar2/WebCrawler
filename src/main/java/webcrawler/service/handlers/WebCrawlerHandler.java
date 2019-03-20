package webcrawler.service.handlers;

import webcrawler.service.domain.webcrawler.WebCrawlerState;

import java.util.List;

public interface WebCrawlerHandler {

    List<WebCrawlerState> getWebCrawlersState();

}
