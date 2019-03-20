package webcrawler.httpParser;

import webcrawler.UrlFrontier.UrlFrontierSingleton;
import org.pmw.tinylog.Logger;
import webcrawler.util.StateHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by shacharlevy on 31/01/2019.
 */
public class HttpRegexSearcher implements Runnable {

    private StateHandler stateHandler;

    /**
     * Regular expression search pattern to search the web
     */
    private Pattern regularExpression;

    /**
     * The HTTP parser
     */
    private HttpParser httpParser;

    public HttpRegexSearcher(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.httpParser = new HttpParser();
        this.regularExpression = Pattern.compile(stateHandler.getConfigFile().getSearchQuery(), Pattern.CASE_INSENSITIVE);
    }


    private void doSearch() {
        while (!Thread.currentThread().isInterrupted()) {
            URL url = getUrl();
            if(url == null){
                Logger.info("no more items left to crawl");
                return;
            }
            List<String> emailsFound = search(url);
            Logger.info(String.format("found emails %s at url %s",emailsFound,url));
            if(!emailsFound.isEmpty()) {
                stateHandler.addResultsToResultsSet(url.getHost(), emailsFound);
            }

        }
    }

    /**
     *
     * @param inputUrl - a URL to search in
     * @return List of strings representing a result set (default will be email's)
     */
    protected List<String> search(URL inputUrl){
        List<String> foundResults = new ArrayList<String>();
        foundResults.addAll(httpParser.searchInPage(inputUrl, regularExpression));
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
}
