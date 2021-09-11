package trvoid.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class BasicJdbcExampleApplication implements CommandLineRunner {
	@Autowired
	DataSource dataSource;

	@Autowired
	CarRepository carRepository;
    
	public static void main(String[] args) {
		SpringApplication.run(BasicJdbcExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("DataSource = " + dataSource);

		System.out.println("== CARS ==");
		List<Car> list = carRepository.findAll();
		list.forEach(x -> System.out.println(x));
		System.out.println("-- CARS --");

		System.exit(0);
	}
}
