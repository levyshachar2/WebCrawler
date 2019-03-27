package webcrawler.util;
import webcrawler.UrlFrontier.UrlFrontierSingleton;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author shachar
 *	The class handles IO operations to local disk
 */
public class FileHandlerIO {


	/**
	 * Gson object for reading/writing to files with json format
	 */
	private Gson gson;

	public FileHandlerIO(){
		this.gson =  new Gson();
		
	}
	/**
	 * Writes the content to the full file path
	 * The function creates the file if doesn't exist
	 * @param content
	 * @param writeOptions - writing options
	 */
	public void writeFile(String fullFilePath ,List<String> content, StandardOpenOption writeOptions){
		try{
			Path file = Paths.get(fullFilePath);
			Files.write(file, content, Charset.forName("UTF-8"),StandardOpenOption.CREATE, writeOptions);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Check if file exist
	 * @param fileName
	 * @return true if file exits
	 */
	public static boolean IsFileExist(String fileName){
		boolean isExists = false;
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
		    isExists = true;
		}
		return isExists;
	}

}
