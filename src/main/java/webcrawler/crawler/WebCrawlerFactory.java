package webcrawler.crawler;

import org.springframework.stereotype.Component;
import webcrawler.service.domain.Consts;

@Component
public class WebCrawlerFactory extends BaseWebCrawlerFactory {

    @Override
    public WebCrawler createWebCrawler(Consts.WebCrawlerType type) {
        WebCrawler webCrawler;
        switch (type) {
            case DFS:
                //TODO : add missing DFS implementation
                webCrawler = new BreadthDepthSearchWebCrawler();
                break;
            case BFS:
                webCrawler = new BreadthDepthSearchWebCrawler();
                break;
            default:
                webCrawler = new BreadthDepthSearchWebCrawler();
                break;
        }
        return webCrawler;
    }
}
