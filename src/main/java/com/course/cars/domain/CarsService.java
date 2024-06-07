package com.course.cars.domain;

import com.course.cars.domain.dto.CarDTO;
import com.course.cars.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CarsService {

    @Autowired
    private CarRepository repository;

    public List<CarDTO> getCars() {
        return repository.findAll().stream().map(CarDTO::create).toList();
    }

    public CarDTO getById(Long id) {
        return repository.findById(id).map(CarDTO::create).orElseThrow(() -> new ObjectNotFoundException("Car not found."));
    }

    public List<CarDTO> getAllByType(String type) {
        return repository.findAllByType(type).stream().map(CarDTO::create).toList();
    }

    public CarDTO create(Car car) {
        Assert.isNull(car.getId(), "Impossible to insert data.");

        return CarDTO.create(repository.save(car));
    }

    public CarDTO update(Long id, Car car) {
        Assert.notNull(id, "Impossible to update data.");

        return CarDTO.create(repository.findById(id).map(c -> {
            c.setName(car.getName());
            c.setType(car.getType());

            repository.save(c);
            return c;
        }).orElseThrow(() -> new RuntimeException("Impossible to update data.")));

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
