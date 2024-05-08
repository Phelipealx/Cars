package com.course.cars.api;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    private final CarService carService = new CarService();

    @GetMapping()
    public List<Car> getCars() {
        return carService.getCars();
    }
}
