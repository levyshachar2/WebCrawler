package webcrawler.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import webcrawler.UrlFrontier.Webpage;
import webcrawler.service.domain.webpages.WebPagesRepository;
import webcrawler.util.StateHandlerSingleton;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
public class StateHandlerScheduledTask {

    @Autowired
    private final WebPagesRepository webPagesRepository;

    public StateHandlerScheduledTask(WebPagesRepository webPagesRepository) {
        this.webPagesRepository = webPagesRepository;
    }


    @Scheduled(fixedRate = 50000)
    public void dumpDataToDB() {
        List<Webpage> webpages = UrlFrontierSingleton.getInstance().getAllUrls();
        Map<URL, List<String>> results = StateHandlerSingleton.getInstance().getUrlToFoundResults();

        webPagesRepository.saveWebPages(webpages);


    }
}
