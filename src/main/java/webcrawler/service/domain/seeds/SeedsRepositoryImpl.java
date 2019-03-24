package webcrawler.service.domain.seeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.service.domain.webpages.WebPagesRepository;

import java.net.URL;

@Service
public class SeedsRepositoryImpl implements SeedsRepository {


    private final WebPagesRepository webPagesRepository;

    @Autowired
    public SeedsRepositoryImpl(WebPagesRepository webPagesRepository) {
        this.webPagesRepository = webPagesRepository;
    }

    @Override
    public boolean addSeed(URL url) {
        return webPagesRepository.addWebPage(url);
    }
}
