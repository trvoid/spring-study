package trvoid.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Car> findAll() {
        List<Car> result = jdbcTemplate.query(
                "SELECT id, model, manufacturer FROM car",
                (rs, rowNum) -> new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("manufacturer")
                )
        );

        return result;
    }
}
