package webcrawler.service.domain.webpages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import webcrawler.UrlFrontier.Webpage;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WebpageRowMapper implements RowMapper<Webpage> {

    private static final Logger logger = LoggerFactory.getLogger(WebpageRowMapper.class);


    @Override
    public Webpage mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            return new Webpage((UUID) rs.getObject("webpage_id"),
                    new URL(rs.getString("webpage_url")),
                    new AtomicInteger(rs.getInt("webpage_priority")),
                    rs.getBoolean("webpage_searched"));
        } catch (MalformedURLException e) {
            logger.error("string url is not a valid URL", e);
            return null;
        }
    }

}
