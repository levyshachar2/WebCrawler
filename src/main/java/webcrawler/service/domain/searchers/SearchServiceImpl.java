package webcrawler.service.domain.searchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearcherService {

    private final SearcherRepository searcherRepository;

    @Autowired
    public SearchServiceImpl(SearcherRepository searcherRepository) {
        this.searcherRepository = searcherRepository;
    }

    @Override
    public boolean addSearcher() {
        return searcherRepository.addSearcher();
    }
}
