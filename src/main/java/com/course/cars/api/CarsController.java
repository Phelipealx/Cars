package com.course.cars.api;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    @Autowired
    private CarsService service;

    @GetMapping
    public Iterable<Car> getAll() {
        return service.getCars();
    }

    @GetMapping("/{id}")
    public Optional<Car> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/type/{type}")
    public Iterable<Car> getByType(@PathVariable String type) {
        return service.getAllByType(type);
    }

    @PostMapping
    public String create(@RequestBody Car car) {
        Car newCar = service.create(car);

        return "Car saved successful - ID: " + newCar.getId();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Car car) {
        Car updatedCar = service.update(id, car);

        return "Car updated successful - ID: " + updatedCar.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);

        return "Car deleted successful";
    }
}
