package com.course.cars.api;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import com.course.cars.domain.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    @Autowired
    private CarsService service;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAll() {
        return ResponseEntity.ok(service.getCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getByType(@PathVariable String type) {
        List<CarDTO> cars = service.getAllByType(type);

        return service.getAllByType(type).isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);
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
