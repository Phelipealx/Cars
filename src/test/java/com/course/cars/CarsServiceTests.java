package com.course.cars;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import com.course.cars.domain.dto.CarDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CarsServiceTests {

    @Autowired
    private CarsService service;

    @Test
    public void testSave() {

        Car car = new Car();
        car.setName("Car Spring Test");
        car.setType("test");

        CarDTO createdCarDTO = service.create(car);
        assertNotNull(createdCarDTO);

        Long id = createdCarDTO.getId();
        Assert.notNull(id);

        Optional<CarDTO> optionalCarDTO = service.getById(id);
        Assert.isTrue(optionalCarDTO.isPresent());

        createdCarDTO = optionalCarDTO.get();
        assertEquals("Car Spring Test", createdCarDTO.getName());
        assertEquals("Car Spring Test", createdCarDTO.getName());

        service.delete(id);

        assertFalse(service.getById(id).isPresent());
    }

    @Test
    public void testList() {
        List<CarDTO> carDTOList = service.getCars();

        assertEquals(30, carDTOList.size());
    }

    @Test
    public void testGetById() {
        Optional<CarDTO> byId = service.getById(11L);

        assertTrue(byId.isPresent());

        CarDTO carDTO = byId.get();

        assertEquals("Ferrari FF", carDTO.getName());
    }

    @Test
    public void testSizeGetByType() {
        assertEquals(10, service.getAllByType("classic").size());
        assertEquals(10, service.getAllByType("sport").size());
        assertEquals(10, service.getAllByType("lux").size());

        assertEquals(0, service.getAllByType("x").size());
    }

}
