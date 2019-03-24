package webcrawler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Shachar Levy
 *	The class represents a configuration file for the web webcrawler.crawler.
 *	Configuration file should have a list of seeds, search query , state file name and results file name
 */
public class ConfigurationFile {
	/**
	 * default regular expression for searching email's - found at http://stackoverflow.com/questions/8204680/java-regex-email
	 */
	private static final String VALID_EMAIL_ADDRESS_REGEX = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";
	
	private static final int DEFAULTDURATION = 1;	
	/**
	 * A given list of seeds to start the crawl from
	 */
	private List<Object> seeds;

	/**
	 * default search query is email's regex
	 */
	private String searchExpression = VALID_EMAIL_ADDRESS_REGEX;
	/**
	 * the full path to the state file
	 */
	private String stateFile;
	/**
	 * the full path to the results file
	 */
	private String resultsFile;
	/**
	 * The log level for the logger class
	 */
	private String logLevel;
	/**
	 * The duration to run the web webcrawler.crawler process
	 */
	private int durationOfWebCrawler;
	/**
	 * Time units for the execution time of the web webcrawler.crawler
	 */	
	private String timeUnit;

	/**
	 * 
	 * @param seeds - collection of seeds (webpages)
	 * @param query - search query regular expression
	 * @param stateFile -  a full path to a file containing the state of the url's
	 * @param resultsFile - a full path to a file containing the results file
	 * @param log - the log level
	 * @param duration - duration of web webcrawler.crawler execution
	 * @param timeUnit - the time unit of the duration, MIN ,SECONDS etc
	 */
	public ConfigurationFile(List<Object> seeds, String query,String stateFile,String resultsFile,String log,int duration,String timeUnit){
		if(seeds == null) this.seeds = new ArrayList<Object>();
		else this.seeds = seeds;
		if(query != null && !query.isEmpty())
			searchExpression = query;	
		
		if(duration < 0) this.setDurationOfWebCrawler(0);
		else this.setDurationOfWebCrawler(duration);
		this.setTimeUnit(timeUnit);
		this.stateFile = stateFile;
		this.resultsFile = resultsFile;
	}
	/**
	 * 
	 * @return results file full path
	 */
	public String getResultsFilesFullPath(){
		return this.resultsFile;
	}
	/**
	 * 
	 * @return the state file full path
	 */
	public String getStateFileNameFullPath(){
		return this.stateFile;
	}
	/**
	 * 
	 * @return a collection of seeds
	 */
	public List<Object> getSeeds() {
		return seeds;
	}
	/**
	 * A setter for seeds
	 * @param seeds
	 */
	public void setSeeds(List<Object> seeds) {
		this.seeds = seeds;
	}
	/**
	 * if searchexpression is empty returns the regex of emails
	 * @return a regex of some search
	 */
	public String getSearchQuery() {
		if(searchExpression.isEmpty())
			searchExpression = VALID_EMAIL_ADDRESS_REGEX;
		return searchExpression;
	}
	/**
	 * A setter for the search query
	 * @param searchQuery
	 */
	public void setSearchQuery(String searchQuery) {
		this.searchExpression = searchQuery;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Configuration : ");
		sb.append(System.getProperty("line.separator"));
		sb.append(String.format("Seeds : %s",seeds.toString()));
		sb.append(System.getProperty("line.separator"));
		sb.append(String.format("Search query : %s",searchExpression));
		return sb.toString();
	}

	/**
	 * If duration is 0 - returns the default duration
	 * @return duration 
	 */
	public int getDurationOfWebCrawler() {
		if(durationOfWebCrawler == 0)
			durationOfWebCrawler = DEFAULTDURATION;
		return durationOfWebCrawler;
	}
	/**
	 * Setter for duration
	 * @param durationOfWebCrawler
	 */
	public void setDurationOfWebCrawler(int durationOfWebCrawler) {
		if(durationOfWebCrawler <= 0)
			this.durationOfWebCrawler = DEFAULTDURATION;
		this.durationOfWebCrawler = durationOfWebCrawler;
	}
	/**
	 * returns the time unit of the webcrawler.crawler - default is minutes
	 * @return
	 */
	public TimeUnit getTimeUnit() {
		TimeUnit timeUnit = TimeUnit.MINUTES;
		switch(this.timeUnit.toUpperCase(Locale.US)){
		case "SECONDS":
			timeUnit = TimeUnit.SECONDS;
			break;
		case "MINUTES":
			timeUnit = TimeUnit.MINUTES;
			break;
		case "HOURS":
			timeUnit = TimeUnit.HOURS;
			break;
		case "DAYS":
			timeUnit = TimeUnit.DAYS;
			break;
		}
		return timeUnit;
	}
	/**
	 * setter for time unit
	 * @param timeUnit
	 */
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}



}
