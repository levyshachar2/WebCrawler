package webcrawler.service.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webcrawler.service.clientContract.Consts;
import webcrawler.service.clientContract.WebCrawlerStateDTO;
import webcrawler.service.domain.webcrawler.WebCrawlerState;
import webcrawler.service.handlers.WebCrawlerHandler;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/webcrawler")
public class WebCrawlerController {

    private final WebCrawlerHandler webCrawlerHandler;
    private final ModelMapper modelMapper;

    @Autowired
    public WebCrawlerController(WebCrawlerHandler webCrawlerHandler, ModelMapper modelMapper) {
        this.webCrawlerHandler = webCrawlerHandler;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/add/{type}" , method = RequestMethod.POST)
    public boolean addWebCrawler(@PathVariable  Consts.WebCrawlerTypeDTO type) {
        Type serviceType = new TypeToken<webcrawler.service.domain.Consts.WebCrawlerType>() {}.getType();
        webcrawler.service.domain.Consts.WebCrawlerType webCrawlerType = modelMapper.map(type,serviceType);
        return webCrawlerHandler.addWebCrawler(webCrawlerType);
    }

    @RequestMapping(value = "/remove/{crawlerId}" , method = RequestMethod.POST)
    public boolean removeWebCrawler(@PathVariable  String crawlerId) {
        return webCrawlerHandler.removeWebCrawler(crawlerId);
    }


}
