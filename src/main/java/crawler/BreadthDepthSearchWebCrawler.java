package crawler;

import UrlFrontier.UrlFrontierSingleton;
import org.pmw.tinylog.Logger;
import org.springframework.util.StopWatch;

import java.net.URL;
import java.util.List;

/**
 * 
 * @author Shachar Levy
 * BFS web crawler , relies on the URL frontier to get first un-crawled web sites.
 * URL's with the same host should have less priority than new domains.
 *
 */
public class BreadthDepthSearchWebCrawler extends WebCrawler {
	public BreadthDepthSearchWebCrawler() {
		super();
	}
	/**
	 * 
	 * @return the first URL in the queue
	 */
	private URL getUrl() {
		return UrlFrontierSingleton.getInstance().getUrl();
	}

	/**
	 * The function update the URL frontier with the newly discovered URL's
	 * @param urlsFound - url's found during the crawl of a specific url
	 */
	private void updateFoundUrls(List<URL> urlsFound) {
		UrlFrontierSingleton.getInstance().updateFoundUrls(urlsFound);
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
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		try{
			while(!Thread.currentThread().isInterrupted()){
				System.out.println(stopwatch.toString());
				URL url = getUrl();
				if(url == null){
					Logger.info("no more items left to crawl");
					return;
				}
				List<URL> linksFound = getHttpParser().getHyperLinks(url);
				Logger.info(linksFound);
				updateFoundUrls(linksFound);

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
