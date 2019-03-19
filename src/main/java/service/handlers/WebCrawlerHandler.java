package service.handlers;

import service.domain.webcrawler.WebCrawlerState;

import java.util.List;

public interface WebCrawlerHandler {

    List<WebCrawlerState> getWebCrawlersState();

}
