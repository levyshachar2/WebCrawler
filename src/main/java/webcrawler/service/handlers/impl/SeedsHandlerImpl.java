package webcrawler.service.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.service.domain.seeds.SeedsService;
import webcrawler.service.handlers.SeedsHandler;

import java.net.URL;

@Service
public class SeedsHandlerImpl implements SeedsHandler {

    private final SeedsService seedsService;

    @Autowired
    public SeedsHandlerImpl(SeedsService seedsService) {
        this.seedsService = seedsService;
    }


    @Override
    public boolean addSeed(URL url) {
        return seedsService.addSeed(url);
    }
}
