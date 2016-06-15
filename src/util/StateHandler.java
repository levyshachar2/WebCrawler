package util;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.pmw.tinylog.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import UrlFrontier.PriorityQueue;
import UrlFrontier.UrlFrontier;

/**
 * 
 * @author shachar
 *	The class handles all the state of the crawler
 *	including the result set and the URL's queue of the web crawler
 *	when the program starts running the state is read from local disk and the web crawler continues from the last execution status
 *	This is done using serialize/desirialize to/from json 
 */
public class StateHandler {
	/**
	 * The host to result set map
	 */
	private ConcurrentHashMap<String,List<String>> urlHostToResultSet;
	/**
	 * The url frontier of the crawler
	 */
	private UrlFrontier urlFrontier;
	/**
	 * the configuration file
	 */
	private ConfigurationFile configfile;
	/**
	 * Gson object for reading/writing to files with json format
	 */
	private Gson gson;
	/**
	 * The priority queue type
	 */
	private static final Type PRIORITYQUEUETYPE = new TypeToken<PriorityQueue>() {
	}.getType();


	/**
	 * The constructor build the state according to the input from the files
	 * if the state is empty then the constructor initialize the data structures 
	 * @param configFile input configuration file
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public StateHandler(ConfigurationFile configFile) throws FileNotFoundException, Exception{
		gson =  new Gson();
		urlHostToResultSet = new ConcurrentHashMap<String, List<String>>();
		this.configfile = configFile;
		buildUrlFrontier(configFile.getStateFileNameFullPath());
		buildEmailsMap(configFile.getResultsFilesFullPath());
	}
	/**
	 * Getter for the URL frontier
	 * @return UrlFrontier
	 */
	public UrlFrontier getUrlFrontier(){
		return this.urlFrontier;
	}
	/**
	 * Getter for the configuration file
	 * @return ConfigurationFile
	 */
	public ConfigurationFile getConfigFile(){
		return this.configfile;
	}
	/**
	 * The function save the state of the crawler to local disk
	 */
	public void flush(){
		saveResults();
		saveUrlsFound();
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
	private void saveResults(){
		String jsonOutput = gson.toJson(urlHostToResultSet);
		List<String> lines = Arrays.asList(jsonOutput);
		FileHandlerIO fileHandler = new FileHandlerIO(configfile.getResultsFilesFullPath());
		fileHandler.WriteFile(lines,StandardOpenOption.TRUNCATE_EXISTING );		
	}
	/**
	 * Saves the state of the URL frontier
	 */
	private void saveUrlsFound(){
		String jsonOutput = gson.toJson(urlFrontier.getQueue());
		List<String> lines = Arrays.asList(jsonOutput);
		FileHandlerIO fileHandler = new FileHandlerIO(configfile.getStateFileNameFullPath());
		fileHandler.WriteFile(lines,StandardOpenOption.TRUNCATE_EXISTING );			
	}
	/**
	 * The function restore the state of the URL frontier if exists
	 * @param fromFileName
	 */
	private void buildUrlFrontier(String fromFileName){
		try{
			JsonReader jsonReader = new JsonReader(new FileReader(fromFileName));
			PriorityQueue queue = gson.fromJson(jsonReader, PRIORITYQUEUETYPE);
			if(queue == null || queue.isEmpty())
				urlFrontier =  new UrlFrontier(configfile.getSeeds());
			else
				urlFrontier = new UrlFrontier(queue);					
		}catch(FileNotFoundException e){
			Logger.warn(String.format("could not file %s", fromFileName));
			urlFrontier =  new UrlFrontier(configfile.getSeeds());
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
				urlHostToResultSet =  new ConcurrentHashMap<String,List<String>>();
			else
				urlHostToResultSet = emailsMap;					
		}
		catch(FileNotFoundException e){
			Logger.warn(String.format("could not file %s", fromFileName));
			urlHostToResultSet =  new ConcurrentHashMap<String,List<String>>();
		}

	}


}
