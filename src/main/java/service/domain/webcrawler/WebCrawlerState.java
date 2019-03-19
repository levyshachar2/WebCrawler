package service.domain.webcrawler;

import service.domain.Consts;

import java.io.Serializable;

public class WebCrawlerState implements Serializable {

    private String id;
    private String urlFrontierDetailed;
    private Consts.WebCrawlerType webCrawlerType;
    private String regularExpression;

    public WebCrawlerState() {

    }


    public WebCrawlerState(String id, String urlFrontierDetailed, Consts.WebCrawlerType webCrawlerType, String regularExpression) {
        this.id = id;
        this.urlFrontierDetailed = urlFrontierDetailed;
        this.webCrawlerType = webCrawlerType;
        this.regularExpression = regularExpression;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlFrontierDetailed() {
        return urlFrontierDetailed;
    }

    public void setUrlFrontierDetailed(String urlFrontierDetailed) {
        this.urlFrontierDetailed = urlFrontierDetailed;
    }

    public Consts.WebCrawlerType getWebCrawlerType() {
        return webCrawlerType;
    }

    public void setWebCrawlerType(Consts.WebCrawlerType webCrawlerType) {
        this.webCrawlerType = webCrawlerType;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }
}
