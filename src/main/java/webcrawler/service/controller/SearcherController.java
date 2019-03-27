package webcrawler.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webcrawler.service.handlers.SearcherHandler;

@RestController
@RequestMapping("/api/searcher")
public class SearcherController {

    private final SearcherHandler searcherHandler;

    @Autowired
    public SearcherController(SearcherHandler searcherHandler) {
        this.searcherHandler = searcherHandler;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public boolean addSearcher() {
        return searcherHandler.addSearcher();
    }
}
