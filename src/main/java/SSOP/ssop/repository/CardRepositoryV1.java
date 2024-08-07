package SSOP.ssop.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepositoryV1 {

    private final JdbcTemplate jdbcTemplate;

    public CardRepositoryV1(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveCard(String card_name, String card_introduction) {
        String sql = "INSERT IMTO card (card_name, card_introduction) VALUES (?, ?)";
        jdbcTemplate.update(sql, card_name, card_introduction);
    }
}
