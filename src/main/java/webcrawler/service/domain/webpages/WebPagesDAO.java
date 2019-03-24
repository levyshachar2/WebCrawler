package webcrawler.service.domain.webpages;

import webcrawler.UrlFrontier.Webpage;

import java.util.UUID;

public interface WebPagesDAO {

    boolean addWebpage(Webpage webpage);

    Webpage getWebPage(UUID webpageId);
}
