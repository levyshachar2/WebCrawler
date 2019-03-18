package crawler;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import UrlFrontier.UrlFrontierSingleton;
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
	 * The HTTP parser
	 */
	private HttpParser httpParser;

	public WebCrawler() {
		this.httpParser = new HttpParser();
	}
	
	/**
	 * each crawler must implement this function - this function searches the web
	 */
	protected abstract void crawl();


	public HttpParser getHttpParser() {
		return httpParser;
	}

}
