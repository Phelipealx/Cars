package com.course.cars.domain;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car(1L, "Astra"));
        cars.add(new Car(2L, "Fiesta"));
        cars.add(new Car(3L, "T-Cross"));

        return cars;
    }
}