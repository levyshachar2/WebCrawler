package webcrawler.UrlFrontier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Shachar Levy
 * The URL frontier class handles the URL's found during the crawl process
 * The URL frontier implements no fairness algorithm, in a way that a page that is already visited will have a decreased priority
 * This is achieved using a priority queue (Min-Heap) where the first element in the queue is the web- page with the minimum priority 
 * this is the web page with the minimum visitations. 
 *
 */
public class UrlFrontierSingleton {
	/**
	 * Priority queue of the url's
	 */
	private PriorityQueue urlQueue;

	private static final Logger logger = LoggerFactory.getLogger(UrlFrontierSingleton.class);


	private UrlFrontierSingleton() {
		initUrlFrontierSingleton();
	}

	private static class SingletonHelper{
		private static final UrlFrontierSingleton INSTANCE = new UrlFrontierSingleton();
	}

	public static UrlFrontierSingleton getInstance(){
		return SingletonHelper.INSTANCE;
	}

	/**
	 * default constructor
	 */
	public void initUrlFrontierSingleton(){
		urlQueue = new PriorityQueue();
	}
	/**
	 * copy constructor
	 * @param otherQueue
	 */
	public void initUrlFrontierSingleton(PriorityQueue otherQueue){
		urlQueue = otherQueue;
	}


	/**
	 * Initiate the URL frontier with given seeds
	 * @param seeds - list of given URL's
	 */
	public void initUrlFrontierSingleton(List<Webpage> seeds) {
		urlQueue = new PriorityQueue(seeds);

	}
	/**
	 * Gets the head of the priority queue, returns the element with the min priority
	 */
	public URL getUrl() {
		if (urlQueue == null) {
			return null;
		}
		logger.debug("removing head of queue " + Thread.currentThread().getId());
		Webpage page = urlQueue.poll();
		if(page != null)
			return page.getURL();
		return null;
	}
	/**
	 * 
	 * @return true IFF queue.isEmpty
	 */
	public boolean isEmpty(){
		return urlQueue.isEmpty();
	}

	public boolean isUrlExist(URL url) {
		if (urlQueue == null || urlQueue.isEmpty()) {
			return false;
		}
		return urlQueue.seenUrl(url);
	}
	/**
	 * 
	 * @return size of queue
	 */
	public int size(){
		return urlQueue.size();
	}

	/**
	 * The function searches for the URL in the queue before inserting the URL.
	 * if the URL is already seen , decrease it's priority and then insert it to the queue.
	 * that way we are making sure little harassment to the same server (politeness)
	 * @param url - a given URL
	 */
	public void insert(URL url){
		logger.debug("UrlFrontier checking if url " + url + " exist " + Thread.currentThread().getId());
		if(urlQueue.seenUrl(url)){
			logger.debug("UrlFrontier seen url " + url +  Thread.currentThread().getId());
			Webpage seenPage = urlQueue.getWebPage(url);
			seenPage.decreasePriority();
			urlQueue.add(seenPage);
		}	
		else{
			urlQueue.add(new Webpage(url));
		}
	}

	/**
	 * @param - a list of URL's to insert to the queue
	 */
	public void updateFoundUrls(Set<URL> urlsFound) {
		for (URL url : urlsFound) {
			insert(url);
		}
	}

	public void updateUrlToSearched(URL url) {
		Webpage webpage = this.urlQueue.getWebPage(url);
		webpage.setSearched(true);
	}

	public List<Webpage> getAllUrls() {
		Webpage[] currentWebpages = urlQueue.getAllWebpages();
		return Arrays.asList(currentWebpages);
	}

}
