package webcrawler.httpParser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpParserSingleton {

    /**
     * HREF constant is the links anchor
     */
    private final static String HREF = "abs:href";
    /**
     * Hashmap of URL to documents - for better performance as network IO is expensive
     */
    private HashMap<URL, Document> urlDocumentMap;

    private final Logger logger = LoggerFactory.getLogger(HttpParserSingleton.class);


    private static HttpParserSingleton ourInstance = new HttpParserSingleton();

    public static HttpParserSingleton getInstance() {
        return ourInstance;
    }

    private HttpParserSingleton() {
        urlDocumentMap = new HashMap<>();
    }

    /**
     * The function searches in a web page for a specific input
     * @param url , a web page to search on
     * @param patternToSearch - a regular expression to search in the body of the HTML
     * @return a collection of found results
     */
    List<String> searchInPage(URL url, Pattern patternToSearch){
        List<String> results = new ArrayList<String>();
        Document doc = getDocument(url);
        if(doc != null && doc.body() != null){
            Matcher matcher = patternToSearch.matcher(doc.html());
            while (matcher.find()) {
                results.add(matcher.group());
            }
        }
        return results;
    }
    /**
     *
     * @param url - url of the relevant document
     * @return - a Document representing the content of that url
     */
    private Document getDocument(URL url){
        Document doc = new Document("");
        if(url == null || url.toString().isEmpty())
            return doc;

        if(urlDocumentMap.containsKey(url))
            //if already exists in hash - return - NOTE : doesn't support changing of web pages
            doc = urlDocumentMap.get(url);
        try {
            Connection connection = Jsoup.connect(url.toString());
            doc = connection.get();
            urlDocumentMap.putIfAbsent(url, doc);

        } catch (IOException e) {
            logger.error(String.format("could not get document from url %s , "
                    + "Exception : %s",url.toString(),e.getMessage()));
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return doc;

    }
    /**
     * The function searches for nested hyper links inside a given web page
     * @param url - the web page to search nested links
     * @return a collection of found URL's
     * @throws IOException
     */
    public List<URL> getHyperLinks(URL url) throws IOException{
        List<URL> foundHyperLinks = new ArrayList<URL>();
        Document doc = getDocument(url);
        Elements links = doc.select("a[href]");
        for(int i=0 ; i<links.size() ; i++){
            try{
                if(!isDynamicContent(links.get(i))){
                    URL foundLink = new URL(links.get(i).attr(HREF));
                    foundHyperLinks.add(foundLink);
                }

            }
            catch (MalformedURLException e){
                logger.warn(e.getLocalizedMessage());
                logger.warn(links.get(i).attr(HREF));
            }
        }
        return foundHyperLinks;
    }
    /**
     *
     * @param element - Element of a web page
     * @return The function returns true IFF the attribute is not empty (in case of dynamic content)
     */
    private boolean isDynamicContent(Element element){
        return element.attr(HREF) == null || element.attr(HREF).isEmpty();

    }


}
