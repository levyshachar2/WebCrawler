package webcrawler.UrlFrontier;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author shachar
 *	The class represents a URL and his priority
 *  each URL is assigned with an integer of number of times this page was visited
 *  The class implements Comparable interface for defining the compare method
 */
public class Webpage implements Comparable<Webpage> {
	/**
	 * Url ID
	 */
	private UUID urlId;

	/**
	 * A URL
	 */
	private URL url;
	/**
	 * the number of times this url was visited
	 */
	private AtomicInteger  numberOfTimesVisited;

	/**
	 * A boolean indicates if this webpage was searched
	 */

	private boolean isSearched;

	public Webpage(UUID urlId, URL url, AtomicInteger numberOfTimesVisited, boolean isSearched) {
		this.urlId = urlId;
		this.url = url;
		this.numberOfTimesVisited = numberOfTimesVisited;
		this.isSearched = isSearched;
	}

	/**
	 * Constructor , initial time visited is 0 
	 * @param url
	 */
	public Webpage(URL url){
		this.urlId = UUID.randomUUID();
		this.url = url;
		this.numberOfTimesVisited = new AtomicInteger(0);
		this.isSearched = false;
	}
	/**
	 * Initiate the object with a specific time visitations
	 * @param url
	 * @param timesVisited
	 */
	public Webpage(URL url,int timesVisited){
		this.url = url;
		this.numberOfTimesVisited = new AtomicInteger(timesVisited);
	}
	/**
	 * Getter for host of the url
	 * @return String representing the host of the url
	 */
	public String getHost(){
		return url.getHost();
	}
	/**
	 * Getter for the url
	 * @return URL
	 */
	public URL getURL(){
		return this.url;
	}
	/**
	 * 
	 * @return visitations number
	 */
	public Integer getTimesVisited(){
		if (numberOfTimesVisited == null) {
			return 0;
		}
		return numberOfTimesVisited.get();
	}
	/**
	 * The function decreased the priority of the web page
	 * this is achieved by increasing the number of visitations of the web page
	 * no sync is needed as the param numberOfTimesVisited is atomic
	 */
	public void decreasePriority(){
		numberOfTimesVisited.incrementAndGet();
	}


	public boolean isSearched() {
		return isSearched;
	}

	public void setSearched(boolean searched) {
		isSearched = searched;
	}

	public UUID getUrlId() {
		return urlId;
	}

	/**
	 * Two web pages are equal if the url is the same
	 */
	@Override
	public boolean equals(Object other){
		if (other == null) {
			return false;
		}
		if(other instanceof Webpage)
			return url.toString().toLowerCase().equals(((Webpage) other).getURL().toString().toLowerCase());
		return false;
	}

	/**
	 * override for the to string method
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Web page url : %s",url.toString()));
		sb.append(System.getProperty("line.separator"));
		sb.append(String.format("web page priority : %d",numberOfTimesVisited.get()));
		return sb.toString();
	}

	/**
	 * two web pages are comparable using their visitations number
	 */
	@Override
	public int compareTo(Webpage pageB) {
		return pageB.getTimesVisited().compareTo(getTimesVisited());
	} 

}

