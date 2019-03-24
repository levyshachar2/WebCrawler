package webcrawler.service.domain.webpages;

import webcrawler.UrlFrontier.Webpage;

import java.net.URL;
import java.util.UUID;

public interface WebPagesRepository {

    Webpage getWebPage(UUID webpageId);

    boolean addWebPage(URL url);
}
