package trvoid.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import trvoid.mybatis.mapper.CarMapper;
import trvoid.mybatis.model.Car;

@RestController
public class MainController {
    @Autowired
    CarMapper carMapper;

    @GetMapping("/")
    public String main() {
        return "hello";
    }

    @GetMapping("/car/{id}")
    public Car car(@PathVariable String id) {
        Car car = carMapper.getCar(id);
        return car;
    }
}
