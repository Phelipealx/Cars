package com.course.cars.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Iterable<Car> findAllByType(String type);
}
