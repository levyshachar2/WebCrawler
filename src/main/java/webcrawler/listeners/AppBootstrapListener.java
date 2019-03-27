package webcrawler.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import webcrawler.UrlFrontier.Webpage;
import webcrawler.service.domain.webpages.WebPagesRepository;

import java.util.List;

@Component
public class AppBootstrapListener implements ApplicationListener<ApplicationReadyEvent> {


    private final WebPagesRepository webPagesRepository;

    @Autowired
    public AppBootstrapListener(WebPagesRepository webPagesRepository) {
        this.webPagesRepository = webPagesRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<Webpage> notSearchedWebpages = webPagesRepository.getAllWebPages(false);
        UrlFrontierSingleton.getInstance().initUrlFrontierSingleton(notSearchedWebpages);

    }
}
