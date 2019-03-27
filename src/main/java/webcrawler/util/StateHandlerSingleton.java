package webcrawler.util;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateHandlerSingleton {

    private Map<URL, List<String>> urlToFoundResults;

    private static StateHandlerSingleton ourInstance = new StateHandlerSingleton();

    public static StateHandlerSingleton getInstance() {
        return ourInstance;
    }

    private StateHandlerSingleton() {
        urlToFoundResults = new HashMap<>();
    }

    public Map<URL, List<String>> getUrlToFoundResults() {
        return urlToFoundResults;
    }

    public void updateFoundResults(URL url, List<String> values) {
        if (urlToFoundResults.containsKey(url)) {
            List<String> currentValues = urlToFoundResults.get(url);
            currentValues.addAll(values);
        } else {
            urlToFoundResults.put(url, values);
        }
    }
}
