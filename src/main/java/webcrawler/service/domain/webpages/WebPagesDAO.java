package webcrawler.service.domain.webpages;

import webcrawler.UrlFrontier.Webpage;

import java.util.List;
import java.util.UUID;

public interface WebPagesDAO {

    boolean addWebpage(Webpage webpage);

    Webpage getWebPage(UUID webpageId);

    boolean addAllWebpages(List<Webpage> webpageList);

    List<Webpage> getAllWebpages(boolean isSearched);
}
