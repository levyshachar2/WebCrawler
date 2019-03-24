package webcrawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import webcrawler.crawler.WebCrawler;
import webcrawler.crawler.WebCrawlerStateListener;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsynchronousService {

    private final SimpleAsyncTaskExecutor taskExecutor;
    private List<WebCrawlerStateListener> listeners;

    private final Logger logger = LoggerFactory.getLogger(AsynchronousService.class);


    @Autowired
    public AsynchronousService(SimpleAsyncTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.listeners = new ArrayList<>();
    }

    public void stopWebCrawler(String id){
        for (WebCrawlerStateListener webCrawlerStateListener : listeners) {
            webCrawlerStateListener.stopWebCrawler(id);
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
