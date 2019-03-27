package webcrawler.service.domain.webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import webcrawler.listeners.StateListener;
import webcrawler.crawler.WebCrawler;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebCrawlerAsynchronousService {

    private final SimpleAsyncTaskExecutor taskExecutor;
    private List<StateListener> listeners;

    private final Logger logger = LoggerFactory.getLogger(WebCrawlerAsynchronousService.class);


    @Autowired
    public WebCrawlerAsynchronousService(SimpleAsyncTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.listeners = new ArrayList<>();
    }

    public void stopWebCrawler(String id){
        for (StateListener stateListener : listeners) {
            stateListener.stop(id);
        }
    }

    public boolean executeAsynchronously(WebCrawler task) {
        try {
            logger.debug("AsynchronousService:: executeAsynchronously ");
            listeners.add(task);
            taskExecutor.execute(task);
            return true;
        } catch (Exception taskExecutorException) {
            logger.error(taskExecutorException.getMessage());
            return false;
        }
    }
}
