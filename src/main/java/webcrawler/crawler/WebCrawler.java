package webcrawler.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.service.domain.Consts;

import java.util.UUID;

/**
 * 
 * @author Shachar Levy
 * class representing a web webcrawler.crawler.
 * there can be different implementations of web crawlers all using different search algorithms.
 * For example - BFS/DFS web crawlers
 */
public abstract class WebCrawler implements Runnable,WebCrawlerStateListener {

	private final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

	private UUID id;

	private Consts.WebCrawlerType webCrawlerType;

	public WebCrawler(Consts.WebCrawlerType webCrawlerType) {
		this.id = UUID.randomUUID();
		this.webCrawlerType = webCrawlerType;
	}


	/**
	 * each webcrawler.crawler must implement this function - this function searches the web
	 */
	protected abstract void crawl();


	Logger getLogger() {
		return logger;
	}

	public Consts.WebCrawlerType getWebCrawlerType() {
		return webCrawlerType;
	}

	public UUID getId() {
		return id;
	}
}
