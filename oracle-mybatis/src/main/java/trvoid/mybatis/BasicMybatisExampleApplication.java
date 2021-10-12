package trvoid.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BasicMybatisExampleApplication implements CommandLineRunner {
	@Autowired
	DataSource dataSource;

	@Autowired
	CarMapper carMapper;

	public static void main(String[] args) {
		SpringApplication.run(BasicMybatisExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("DataSource = " + dataSource);

		if (carMapper.countTable() == 0) {
			carMapper.createTable();
			System.out.println("** Created a table: CAR");

			int carId = (new Random().nextInt()) % 100;

			Car car1 = new Car(carId + 0, "J1", "JELLY", "1");
			carMapper.insertCar((car1));
			System.out.println(String.format("** Inserted a car: %s", car1.toString()));

			Car car2 = new Car(carId + 1, "T1", "TEAL", "2");
			carMapper.insertCar((car2));
			System.out.println(String.format("** Inserted a car: %s", car2.toString()));

			Car car3 = new Car(carId + 2, "W1", "WISDOM", null);
			carMapper.insertCar((car3));
			System.out.println(String.format("** Inserted a car: %s", car3.toString()));
		}

		System.out.println("== CARS ==");
		List<Car> list = carMapper.findAll();
		list.forEach(x -> System.out.println(x));
		System.out.println("-- CARS --");

		System.exit(0);
	}
}
