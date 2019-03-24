package webcrawler.UrlFrontier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 
 * @author shachar
 * The class represents a queue with priority.
 * The queue itself is a blocking queue of web pages which is because the priority queue is shared between multiple crawlers
 * The class saves a hash of hosts to found url's
 */
public class PriorityQueue {
	/**
	 * The blocking priority queue
	 */
	private PriorityBlockingQueue<Webpage> queue;
	/**
	 * hash map of Host,Collection<webpages>
	 */
	private ConcurrentHashMap<String,ArrayList<Webpage>> hostToUrlsMap;
	/**
	 * a collection of given seeds
	 */
	private List<Object> seeds;

	private final Logger logger = LoggerFactory.getLogger(PriorityQueue.class);


	/**
	 * Default constructor
	 */
	public PriorityQueue(){
		this.seeds = new ArrayList<>();
		this.hostToUrlsMap = new ConcurrentHashMap<>();
		this.queue = new PriorityBlockingQueue<>();
	}
	/**
	 * 
	 * @param seeds - initialize with given seeds
	 */
	public PriorityQueue(List<Object> seeds){
		this.seeds = seeds;
		this.ConstructInitialDataStructures();
	}
	/**
	 * The function add a web page to the hash and to the queue
	 * The function doesn't add a web page if the web page already exists
	 * @param webpage
	 */
	public void add(Webpage webpage){
		if(!hostToUrlsMap.containsKey(webpage.getHost())){
			ArrayList<Webpage> urls = new ArrayList<Webpage>();
			urls.add(webpage);
			hostToUrlsMap.put(webpage.getHost(), urls);
			queue.add(webpage);
		}
		else{
			ArrayList<Webpage> webPagesFoundAtHost = hostToUrlsMap.get(webpage.getHost());
			if(!webPagesFoundAtHost.contains(webpage)){
				webpage.decreasePriority(); //for politeness
				webPagesFoundAtHost.add(webpage);
			}
		}			
	}

	/**
	 * When a web page is polled from the queue his priority is decreased
	 * @return null if the queue is empty , or the first element in the priority queue
	 */
	public Webpage poll(){
		if(isEmpty()){
			return null;
		}
		logger.debug("min priority from queue");
		Webpage webpage = queue.poll();
		logger.debug(String.format("is ---- %d",webpage.getTimesVisited()));
		logger.debug(printPriorities());
		webpage.decreasePriority();			

		return webpage;
	}
	/**
	 * 
	 * @return the first element from the priority queue
	 */
	public Webpage remove(){
		if(isEmpty()){
			return null;
		}
		Webpage webpage = queue.remove();
		webpage.decreasePriority();
		return webpage;		
	}
	/**
	 * The function checks if the queue is empty
	 * @return true if queue.size() = 0
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	/**
	 * The function returns the queue size
	 * @return the elements found in the queue
	 */
	public int size(){
		return queue.size();
	}
	/**
	 * 
	 * @param url - a given URL
	 * @return - true if the URL is already exists
	 */
	public boolean seenUrl(URL url){
		if(seenHost(url)){
			Webpage webpage = new Webpage(url);
			return hostToUrlsMap.get(url.getHost()).contains(webpage);
		}
		return false;
	}
	/**
	 * For a given url returns if the host of the url is already defined
	 * @param url
	 * @return true if map conatins url host
	 */
	public boolean seenHost(URL url){
		return hostToUrlsMap.containsKey(url.getHost());
	}
	/**
	 * A getter for the webpage from the hash
	 * @param url
	 * @return the Web page exists in our hash 
	 */
	public Webpage getWebPage(URL url){
		Webpage page = null;
		if(seenHost(url)){
			List<Webpage> urlsOfHost = hostToUrlsMap.get(url.getHost());
			page= getWebPageFromList(urlsOfHost, url);
		}
		return page;
	}
	/**
	 * helper function to return the web page from a collection of web pages by url
	 * @param webPages collection of web pages
	 * @param url an input url
	 * @return web page corresponding to the web page object of the given url
	 */
	private Webpage getWebPageFromList(List<Webpage> webPages,URL url){
		for(Webpage page : webPages){
			if(page.getURL() == url)
				return page;
		}
		return null;

	}

	/**
	 * The function constructs the Hash and the priority queue data structures
	 */
	private void ConstructInitialDataStructures(){

		this.queue = new PriorityBlockingQueue<Webpage>(seeds.size());
		this.hostToUrlsMap = new ConcurrentHashMap<>(seeds.size());
		for (int i = 0; i < seeds.size(); ++i) {
			Object seed = (Object) seeds.get(i);
			try{
				URL url = new URL(seed.toString());
				Webpage page = new Webpage(url);
				ArrayList<Webpage> initialList = new ArrayList<Webpage>();
				initialList.add(page);
				queue.add(page); //page is inserted with default priority 0 
				hostToUrlsMap.put(url.getHost(),initialList);
			} catch (MalformedURLException e) {
				logger.warn("one of the given webpages is malformed");
				logger.info(e.getMessage());
			}
		}
	}
	/**
	 * The function return a string representing the class 
	 * @return string representing the class
	 */
	private String printPriorities(){
		StringBuilder sb = new StringBuilder();
		for(Webpage web : queue) { 
			sb.append(System.getProperty("line.separator"));
			sb.append(web.toString()); 
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

}
