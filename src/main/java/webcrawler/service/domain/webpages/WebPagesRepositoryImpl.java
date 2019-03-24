package webcrawler.service.domain.webpages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import webcrawler.UrlFrontier.Webpage;

import java.net.URL;
import java.util.UUID;

@Service
public class WebPagesRepositoryImpl implements WebPagesRepository {

    private final WebPagesDAO webPagesDAO;

    @Autowired
    public WebPagesRepositoryImpl(WebPagesDAO webPagesDAO) {
        this.webPagesDAO = webPagesDAO;
    }

    @Override
    public Webpage getWebPage(UUID webpageId) {
        return webPagesDAO.getWebPage(webpageId);
    }

    @Override
    public boolean addWebPage(URL url) {
        UrlFrontierSingleton.getInstance().insert(url);
        Webpage webpage = new Webpage(url);
        return webPagesDAO.addWebpage(webpage);
    }
}
