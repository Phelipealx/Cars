package com.course.cars.domain;

import com.course.cars.domain.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarsService {

    @Autowired
    private CarRepository repository;

    public List<CarDTO> getCars() {
        return repository.findAll().stream().map(CarDTO::create).toList();
    }

    public Optional<CarDTO> getById(Long id) {
        return repository.findById(id).map(CarDTO::create);
    }

    public List<CarDTO> getAllByType(String type) {
        return repository.findAllByType(type).stream().map(CarDTO::create).toList();
    }

    public Car create(Car car) {
        return repository.save(car);
    }

    public Car update(Long id, Car car) {
        Assert.notNull(id, "Impossible to update data.");

        return repository.findById(id).map(c -> {
            c.setName(car.getName());
            c.setType(car.getType());

            repository.save(c);
            return c;
        }).orElseThrow(() -> new RuntimeException("Impossible to update data."));

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
