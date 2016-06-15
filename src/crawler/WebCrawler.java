package crawler;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import UrlFrontier.UrlFrontier;
import httpParser.HttpParser;
import util.StateHandler;

/**
 * 
 * @author Shachar Levy
 * class representing a web crawler.
 * there can be different implementations of web crawlers all using different search algorithms.
 * For example - BFS/DFS web crawlers
 */
public abstract class WebCrawler implements Runnable {
	/**
	 * The URL handler
	 */
	private UrlFrontier urlFrontier;
	/**
	 * The HTTP parser 
	 */
	private HttpParser httpParser;
	/**
	 * Regular expression search pattern to search the web
	 */
	private Pattern regularExpression;
	/**
	 * The state handler for the crawler
	 */
	private StateHandler stateHandler;
	
	/**
	 * each crawler must implement this function - this function searches the web
	 */
	protected abstract void crawl();
	
	/**
	 * abstract search function
	 * @param input URL 
	 * @return list of results 
	 */
	protected abstract List<String> search(URL input);

	/**
	 * 
	 * @return UrlFrontier object with all the URL's
	 */
	public UrlFrontier getUrlFrontier() {
		return urlFrontier;
	}

	/**
	 * sets the url frontier
	 * @param urlFrontier
	 */
	public void setUrlFrontier(UrlFrontier urlFrontier) {
		this.urlFrontier = urlFrontier;
	}
	/**
	 * getter for http parser
	 * @return UrlFrontier
	 */
	public HttpParser getHttpParser() {
		return httpParser;
	}
	/**
	 * Setter for http parser
	 * @param httpParser
	 */
	public void setHttpParser(HttpParser httpParser) {
		this.httpParser = httpParser;
	}
	
	/**
	 * Getter for the search pattern
	 * @return Pattern
	 */
	public Pattern getRegularExpression() {
		return regularExpression;
	}
	/**
	 * Setter for the search pattern
	 * @param regularExpression - Pattern
	 */
	public void setRegularExpression(Pattern regularExpression) {
		this.regularExpression = regularExpression;
	}
	/**
	 * Getter for StateHandler
	 * @return StateHandler
	 */
	public StateHandler getStateHandler() {
		return stateHandler;
	}
	/**
	 * Setter for StateHandler
	 * @param stateHandler
	 */
	public void setStateHandler(StateHandler stateHandler) {
		this.stateHandler = stateHandler;
	}
	
}
