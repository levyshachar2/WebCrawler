package crawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.pmw.tinylog.Logger;

import httpParser.HttpParser;
import util.StateHandler;

/**
 * 
 * @author Shachar Levy
 * BFS web crawler , relies on the URL frontier to get first un-crawled web sites.
 * URL's with the same host should have less priority than new domains.
 *
 */
public class BreadhDepthSearchWebCrawler extends WebCrawler {
	public BreadhDepthSearchWebCrawler(String searchQuery,StateHandler stateHandler) {
		if(stateHandler.getUrlFrontier() == null)
			throw new IllegalArgumentException("Url handler was null at constructor");
		setUrlFrontier(stateHandler.getUrlFrontier());
		this.setHttpParser(new HttpParser());
		this.setRegularExpression(Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE));
		this.setStateHandler(stateHandler);

	}
	/**
	 * 
	 * @return the first URL in the queue
	 */
	private URL getUrl() {
		return this.getUrlFrontier().getUrl();
	}
	@Override
	/**
	 * 
	 * @param inputUrl - a URL to search in
	 * @return List of strings representing a result set (default will be email's)
	 */
	protected List<String> search(URL inputUrl){
		List<String> foundResults = new ArrayList<String>();
		foundResults.addAll(getHttpParser().searchInPage(inputUrl, getRegularExpression()));
		return foundResults;
	}

	/**
	 * The function update the URL frontier with the newly discovered URL's
	 * @param urlsFound - url's found during the crawl of a specific url
	 */
	private void updateFoundUrls(List<URL> urlsFound) {
		this.getUrlFrontier().updateFoundUrls(urlsFound);
	}
	/**
	 * The crawl function , with basic algorithm
	 * while not aborted && more URL to process
	 * get first URL
	 * search in URL
	 * update results
	 * find links
	 * update queue
	 * 
	 */
	protected void crawl() {
		Logger.info(String.format("Thread %d started running", Thread.currentThread().getId()));
		try{
			
			while(!Thread.currentThread().isInterrupted()){
				URL url = getUrl();
				if(url == null){
					Logger.info("no more items left to crawl");
					return;
				}
				List<String> emailsFound = search(url);
				Logger.info(String.format("found emails %s at url %s",emailsFound,url));
				List<URL> linksFound = getHttpParser().getHyperLinks(url);
				Logger.info(linksFound);
				updateFoundUrls(linksFound);
				if(!emailsFound.isEmpty())
					getStateHandler().addResultsToResultsSet(url.getHost(), emailsFound);

			}			
		}
		catch(Exception e){
			Logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * The function from Runnable interface
	 * Running the web crawler
	 */
	@Override
	public void run() {
		this.crawl();

	}


}
