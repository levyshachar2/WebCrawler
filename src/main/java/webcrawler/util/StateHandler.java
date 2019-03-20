package webcrawler.util;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.pmw.tinylog.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import webcrawler.UrlFrontier.PriorityQueue;
import webcrawler.UrlFrontier.UrlFrontierSingleton;

/**
 * 
 * @author shachar
 *	The class handles all the state of the webcrawler.crawler
 *	including the result set and the URL's queue of the web webcrawler.crawler
 *	when the program starts running the state is read from local disk and the web webcrawler.crawler continues from the last execution status
 *	This is done using serialize/desirialize to/from json 
 */
public class StateHandler {
	/**
	 * The host to result set map
	 */
	private ConcurrentHashMap<String,List<String>> urlHostToResultSet;
	/**
	 * the configuration file
	 */
	private ConfigurationFile configfile;
	/**
	 * The priority queue type
	 */
	private static final Type PRIORITYQUEUETYPE = new TypeToken<PriorityQueue>() {
	}.getType();

	private ExecutorService executor;

	private Gson gson;


	/**
	 * The constructor build the state according to the input from the files
	 * if the state is empty then the constructor initialize the data structures 
	 * @param configFile input configuration file
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public StateHandler(ConfigurationFile configFile) throws Exception{
		urlHostToResultSet = new ConcurrentHashMap<>();
		this.configfile = configFile;
		gson = new Gson();
		buildUrlFrontier(configFile.getStateFileNameFullPath());
		buildEmailsMap(configFile.getResultsFilesFullPath());
		executor = Executors.newSingleThreadExecutor();

	}
	/**
	 * Getter for the configuration file
	 * @return ConfigurationFile
	 */
	public ConfigurationFile getConfigFile(){
		return this.configfile;
	}

	/**
	 * The function save the state of the webcrawler.crawler to local disk
	 */
	public void flush(){
		saveDataToDisk();
	}
	/**
	 * Add a list of string at a given key to the hash map
	 * @param key the given key
	 * @param values the values to add
	 */
	public void addResultsToResultsSet(String key,List<String> values){
		if(urlHostToResultSet.containsKey(key)){
			List<String> preValues = urlHostToResultSet.get(key);
			preValues.addAll(values);
		}
		else{
			urlHostToResultSet.putIfAbsent(key, values);
		}

	}
	/**
	 * Save the results to local results file
	 * Serialize the results using GSON - overwriting existing data (as previous data is already loaded)
	 */
	private void saveDataToDisk(){
		FileHandlerIO fileHandler = new FileHandlerIO(this);
		executor.execute(fileHandler);
	}

	/**
	 * The function restore the state of the URL frontier if exists
	 * @param fromFileName
	 */
	private void buildUrlFrontier(String fromFileName){
		try{
			JsonReader jsonReader = new JsonReader(new FileReader(fromFileName));
			PriorityQueue queue = gson.fromJson(jsonReader, PRIORITYQUEUETYPE);
			if(queue == null || queue.isEmpty()) {
				UrlFrontierSingleton.getInstance().initUrlFrontierSingleton(configfile.getSeeds());
			}
			else {
				UrlFrontierSingleton.getInstance().initUrlFrontierSingleton(queue);
			}
		} catch(FileNotFoundException e){
			Logger.warn(String.format("could not file %s", fromFileName));
			UrlFrontierSingleton.getInstance().initUrlFrontierSingleton(configfile.getSeeds());
		}

	}
	

	/**
	 * the function restores the state of the resultsSetMap
	 * @param fromFileName
	 */
	private void buildEmailsMap(String fromFileName){
		try{
			JsonReader jsonReader = new JsonReader(new FileReader(fromFileName));
			ConcurrentHashMap<String,List<String>> emailsMap = gson.fromJson(jsonReader, ConcurrentHashMap.class);
			if(emailsMap == null || emailsMap.isEmpty())
				urlHostToResultSet = new ConcurrentHashMap<>();
			else
				urlHostToResultSet = emailsMap;					
		}
		catch(FileNotFoundException e){
			Logger.warn(String.format("could not file %s", fromFileName));
			urlHostToResultSet = new ConcurrentHashMap<>();
		}
	}

	public ConcurrentHashMap<String,List<String>> getUrlHostToResultSet() {
		return urlHostToResultSet;
	}

	public void shutdown() {
		Logger.info("Shutting down State Handler executer");
		executor.shutdown();
	}

}
