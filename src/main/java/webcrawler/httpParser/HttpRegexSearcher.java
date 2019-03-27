package webcrawler.httpParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.listeners.StateListener;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import webcrawler.util.StateHandlerSingleton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by shacharlevy on 31/01/2019.
 */
public class HttpRegexSearcher implements Runnable, StateListener {
    /**
     * Regular expression search pattern to search the web
     */
    private Pattern regularExpression;

    private UUID searcherId;

    private final Logger logger = LoggerFactory.getLogger(HttpRegexSearcher.class);

    public HttpRegexSearcher(String regularExpression) {
        this.regularExpression = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE);
        this.searcherId = UUID.randomUUID();
    }

    private void doSearch() {
        while (!Thread.currentThread().isInterrupted()) {
            URL url = getUrl();
            if(url == null){
                logger.info("no more items left to crawl");
                try{
                    UrlFrontierSingleton.getInstance().wait();
                } catch (InterruptedException e) {
                    logger.error("interrupt exception");
                }
                continue;
            }
            List<String> emailsFound = search(url);
            UrlFrontierSingleton.getInstance().updateUrlToSearched(url);
            logger.info(String.format("found emails %s at url %s",emailsFound,url));
            if(!emailsFound.isEmpty()) {
                StateHandlerSingleton.getInstance().updateFoundResults(url,emailsFound);
            }
        }
    }

    /**
     *
     * @param inputUrl - a URL to search in
     * @return List of strings representing a result set (default will be email's)
     */
    private List<String> search(URL inputUrl){
        List<String> foundResults = new ArrayList<>(HttpParserSingleton.getInstance().searchInPage(inputUrl, regularExpression));
        return foundResults;
    }

    /**
     *
     * @return the first URL in the queue
     */
    private URL getUrl() {
        return UrlFrontierSingleton.getInstance().getUrl();
    }

    @Override
    public void run() {
        doSearch();
    }

    public UUID getSearcherId() {
        return searcherId;
    }

    @Override
    public void stop(String id) {
        if (searcherId.toString().equals(id)) {
            Thread.currentThread().interrupt();
        }
    }
}
