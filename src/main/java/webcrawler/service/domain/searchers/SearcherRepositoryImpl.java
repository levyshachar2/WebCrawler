package webcrawler.service.domain.searchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webcrawler.httpParser.HttpRegexSearcher;
import webcrawler.service.domain.Consts;
import webcrawler.service.domain.configurations.ConfigurationRepository;

@Service
public class SearcherRepositoryImpl implements SearcherRepository {

    private final SearcherDAO searcherDAO;

    private final ConfigurationRepository configurationRepository;

    private final SearcherAsynchronousService searcherAsynchronousService;

    @Autowired
    public SearcherRepositoryImpl(SearcherDAO searcherDAO, ConfigurationRepository configurationRepository, SearcherAsynchronousService searcherAsynchronousService) {
        this.searcherDAO = searcherDAO;
        this.configurationRepository = configurationRepository;
        this.searcherAsynchronousService = searcherAsynchronousService;
    }

    @Override
    public boolean addSearcher() {
        String globalRegEx = configurationRepository.getConfiguration(Consts.ConfigurationKeys.REGULAR_EXPRESSION, null);
        HttpRegexSearcher httpRegexSearcher = new HttpRegexSearcher(globalRegEx);
        searcherAsynchronousService.executeAsynchronously(httpRegexSearcher);
        return searcherDAO.addSearcher(httpRegexSearcher.getSearcherId());
    }
}
