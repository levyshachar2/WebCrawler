package util;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * 
 * @author shachar
 *	The class handles IO operations to local disk
 */
public class FileHandlerIO {
	/**
	 * the file to work on
	 */
	private String FullFilePath;
	/**
	 * initialize the file path
	 * @param filePath
	 */
	public FileHandlerIO(String filePath){
		this.FullFilePath = filePath;
		
	}
	/**
	 * Writes the content to the full file path
	 * The function creates the file if doesn't exist
	 * @param content
	 * @param writeOptions - writing options
	 */
	public void WriteFile(List<String> content,StandardOpenOption writeOptions){
		try{
			Path file = Paths.get(FullFilePath);
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
