package webcrawler.service.clientContract;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlDTO {

    private String url;
    private String protocol;

    public UrlDTO(String url, String protocol) {
        this.url = url;
        this.protocol = protocol;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUrl() {
        return this.url;
    }
    public URL getUrlAsUrlType() throws MalformedURLException {
        return new URL(String.format("%s://%s",protocol,url));
    }
}
