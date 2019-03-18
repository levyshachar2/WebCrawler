import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import UrlFrontier.UrlFrontierSingleton;
import crawler.BreadthDepthSearchWebCrawler;
import httpParser.HttpRegexSearcher;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.rules.Stopwatch;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import util.ConfigurationFile;
import util.FileHandlerIO;
import util.StateHandler;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
	private final static int UPPERBOUNDNUMBEROFTHREADS = 5;
	private static final String LOGFILENAME = "log.txt";
	
	public static void main(String[] args) {
		try{
			ConfigurationFile configFile = ReadInitialParams(args);
			//configure the logger
			Configurator.defaultConfig()
			.writer(new FileWriter(LOGFILENAME))
			.level(configFile.getLogLevel())
			.activate();
			Logger.info(configFile.toString());
			StateHandler stateHandler = new StateHandler(configFile);
			int numberOfThreads = Math.min(UrlFrontierSingleton.getInstance().size(), UPPERBOUNDNUMBEROFTHREADS);
			ExecutorService crawlers = Executors.newFixedThreadPool(numberOfThreads);
			ExecutorService searchers = Executors.newFixedThreadPool(numberOfThreads);
			Runnable crawlerTask =  new BreadthDepthSearchWebCrawler();
			Runnable searchTask = new HttpRegexSearcher(stateHandler);

			for(int i=0; i< numberOfThreads ; i++){
				crawlers.submit(crawlerTask);
				searchers.submit(searchTask);
			}
			shutdownAndAwaitTermination(crawlers, stateHandler);
			shutdownAndAwaitTermination(searchers, stateHandler);

		}
		catch(FileNotFoundException e){
			System.out.println(System.out.printf("could not find file %s please check if file exists", args[0]));
		}
		catch(IllegalArgumentException e){
			System.out.println(System.out.printf("%s", e.toString()));
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(System.out.printf("unhandled exception %s", e.toString()));
		}


	}

	/**
	 * The function waits a given amount of time to wait until the web crawler is complete
	 * then sends interrupt when that time comes and gracefully shut it down
	 * @param pool - the pool of threads to shut down
	 * @param stateHandler - state handler for handling the state of the web crawler
	 */
	private static void shutdownAndAwaitTermination(ExecutorService pool,StateHandler stateHandler) {
		pool.shutdown();
		try {
			// Wait for existing tasks to terminate
			if (!pool.awaitTermination(stateHandler.getConfigFile().getDurationOfWebCrawler(), stateHandler.getConfigFile().getTimeUnit())) {  
				pool.shutdownNow(); 

				if (!pool.awaitTermination(5, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			pool.shutdownNow();
			Thread.currentThread().interrupt();
		}
		finally {
			stateHandler.flush();
			stateHandler.shutdown();
		}
	} 
	
	/**
	 * Read the initial param from argument - the location of the configuration file
	 * @param args - arguments for the web crawler
	 * @return ConfigurationFile containing the necessary configuration for the web crawler
	 * @throws FileNotFoundException in case the configuration file doesn't exist
	 */
	private static ConfigurationFile ReadInitialParams(String[] args) throws FileNotFoundException {
		if(args.length == 0)
			throw new IllegalArgumentException("There were no arguments given , please enter a path for a valid configuration file");
		if(!FileHandlerIO.IsFileExist(args[0]))
			throw new FileNotFoundException(String.format("file %s was not found", args[0]));
		Gson gson = new Gson();
		JsonReader jsonReader = new JsonReader(new FileReader(args[0]));
		ConfigurationFile configFile = gson.fromJson(jsonReader, ConfigurationFile.class);		
		return configFile;
	}

}
