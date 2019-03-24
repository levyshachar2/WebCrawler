package webcrawler.crawler;

import webcrawler.service.domain.Consts;

public abstract class BaseWebCrawlerFactory {

    public abstract WebCrawler createWebCrawler(Consts.WebCrawlerType type);
}
