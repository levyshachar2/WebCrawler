package webcrawler.service.domain.searchers;

import java.util.UUID;

public interface SearcherDAO {

    boolean addSearcher(UUID searcherId);
}
