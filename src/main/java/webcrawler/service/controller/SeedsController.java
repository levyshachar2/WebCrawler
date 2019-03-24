package webcrawler.service.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webcrawler.service.clientContract.UrlDTO;
import webcrawler.service.handlers.SeedsHandler;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/api/seeds")
public class SeedsController {

    private final SeedsHandler seedsHandler;
    private final ModelMapper modelMapper;

    @Autowired
    public SeedsController(SeedsHandler seedsHandler, ModelMapper modelMapper) {
        this.seedsHandler = seedsHandler;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/add/url" , method = RequestMethod.POST)
    public boolean addSeedUrl(@RequestBody UrlDTO urlDTO) throws MalformedURLException {
        URL parsedUrl = urlDTO.getUrlAsUrlType();
        return seedsHandler.addSeed(parsedUrl);
    }
}
