package com.course.cars.domain;

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

    public Iterable<Car> getCars() {
        return repository.findAll();
    }

    public Optional<Car> getById(Long id) {
        return repository.findById(id);
    }

    public Iterable<Car> getAllByType(String type) {
        return repository.findAllByType(type);
    }

    public Car create(Car car) {
        return repository.save(car);
    }

    public Car update(Long id, Car car) {
        Assert.notNull(id, "Impossible to update data.");

        return getById(id).map(c -> {
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
