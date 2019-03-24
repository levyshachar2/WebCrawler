package webcrawler.service.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import webcrawler.service.domain.webcrawler.WebCrawlerRepository;

@Component
public class ServiceInitializingBean implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInitializingBean.class);

    private final Environment environment;


    @Autowired
    public ServiceInitializingBean(Environment environment, WebCrawlerRepository webCrawlerRepository) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug(environment.toString());

    }
}
