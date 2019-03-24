package webcrawler.service.domain.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import webcrawler.service.domain.webpages.WebPagesRepository;

import java.net.URL;

@Service
public class SeedsServiceImpl implements SeedsService {

    private final SeedsRepository seedsRepository;
    private final WebPagesRepository webPagesRepository;

    @Autowired
    public SeedsServiceImpl(SeedsRepository seedsRepository, WebPagesRepository webPagesRepository) {
        this.seedsRepository = seedsRepository;
        this.webPagesRepository = webPagesRepository;
    }


    @Override
    public boolean addSeed(URL url) {
        if (UrlFrontierSingleton.getInstance().isUrlExist(url)) {
            return true;
        }
        return seedsRepository.addSeed(url);
    }
}
