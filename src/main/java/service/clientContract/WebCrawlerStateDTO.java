package service.clientContract;

import java.io.Serializable;

public class WebCrawlerStateDTO implements Serializable {

    private String id;
    private String urlFrontierDetailed;
    private Consts.WebCrawlerTypeDTO webCrawlerTypeDTO;
    private String regularExpression;

    public WebCrawlerStateDTO() {

    }


    public WebCrawlerStateDTO(String id, String urlFrontierDetailed, Consts.WebCrawlerTypeDTO webCrawlerTypeDTO, String regularExpression) {
        this.id = id;
        this.urlFrontierDetailed = urlFrontierDetailed;
        this.webCrawlerTypeDTO = webCrawlerTypeDTO;
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

    public Consts.WebCrawlerTypeDTO getWebCrawlerTypeDTO() {
        return webCrawlerTypeDTO;
    }

    public void setWebCrawlerTypeDTO(Consts.WebCrawlerTypeDTO webCrawlerTypeDTO) {
        this.webCrawlerTypeDTO = webCrawlerTypeDTO;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

}
