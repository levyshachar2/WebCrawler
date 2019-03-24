package webcrawler.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class BaseDAOImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected JdbcTemplate getJdbc()
    {
        return jdbcTemplate;
    }

    protected NamedParameterJdbcTemplate getNamedJdbc() {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
