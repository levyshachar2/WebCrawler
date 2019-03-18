package UrlFrontier;

import java.net.URL;
import java.util.List;

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

	private UrlFrontierSingleton() {

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
	public void initUrlFrontierSingleton(List<Object> seeds) {
		urlQueue = new PriorityQueue(seeds);
	}

	/**
	 * Gets the head of the priority queue, returns the element with the min priority
	 */
	public URL getUrl() {
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
	private void insert(URL url){
		if(urlQueue.seenUrl(url)){
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
	public void updateFoundUrls(List<URL> urlsFound) {
		for (URL url : urlsFound) {
			insert(url);
		}
	}
	
	/**
	 * 
	 * @return the queue
	 */
	public PriorityQueue getQueue(){
		return this.urlQueue;
	}

}
