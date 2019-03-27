package webcrawler.crawler;

import webcrawler.UrlFrontier.UrlFrontierSingleton;
import org.springframework.util.StopWatch;
import webcrawler.httpParser.HttpParserSingleton;
import webcrawler.service.domain.Consts;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Shachar Levy
 * BFS web webcrawler.crawler , relies on the URL frontier to get first un-crawled web sites.
 * URL's with the same host should have less priority than new domains.
 *
 */
public class BreadthDepthSearchWebCrawler extends WebCrawler {

	private static final Consts.WebCrawlerType type = Consts.WebCrawlerType.BFS;

	public BreadthDepthSearchWebCrawler() {
		super(type);
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
	private void updateFoundUrls(Set<URL> urlsFound) {
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
		getLogger().info(String.format("Thread %d started running", Thread.currentThread().getId()));
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		try{
			while(!Thread.currentThread().isInterrupted()){
				System.out.println(stopwatch.toString());
				URL url = getUrl();
				if(url == null){
					getLogger().info("no more items left to crawl");
					return;
				}
				Set<URL> linksFound = HttpParserSingleton.getInstance().getHyperLinks(url);
				for (URL link : linksFound) {
					getLogger().info(String.format("Found link %s", link));
				}
				updateFoundUrls(linksFound);

			}
		}
		catch(Exception e){
			getLogger().error(e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * The function from Runnable interface
	 * Running the web webcrawler.crawler
	 */
	@Override
	public void run() {
		this.crawl();
	}


	@Override
	public void stop(String id) {
		if (this.getId().toString().equals(id)) {
			Thread.currentThread().interrupt();
		}
	}

}
