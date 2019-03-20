package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.clientContract.WebCrawlerStateDTO;
import service.domain.webcrawler.WebCrawlerState;
import service.handlers.WebCrawlerHandler;

import java.util.List;

@RestController
@RequestMapping("/api/webcrawler")
public class WebCrawlerController {

    private final WebCrawlerHandler webCrawlerHandler;

    @Autowired
    public WebCrawlerController(WebCrawlerHandler webCrawlerHandler) {
        this.webCrawlerHandler = webCrawlerHandler;
    }

    @RequestMapping(value = "/currentState" , method = RequestMethod.GET)
    public List<WebCrawlerStateDTO> getCurrentState() {
        List<WebCrawlerState> webCrawlerStates = webCrawlerHandler.getWebCrawlersState();
        List<WebCrawlerStateDTO> webCrawlerStateDTOS = null;
        return webCrawlerStateDTOS;
    }
}