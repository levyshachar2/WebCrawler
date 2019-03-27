package webcrawler.service.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.service.domain.searchers.SearcherService;
import webcrawler.service.handlers.SearcherHandler;
@Service
public class SearcherHandlerImpl implements SearcherHandler {

    private final SearcherService searcherService;

    @Autowired
    public SearcherHandlerImpl(SearcherService searcherService) {
        this.searcherService = searcherService;
    }

    @Override
    public boolean addSearcher() {
        return searcherService.addSearcher();
    }
}
