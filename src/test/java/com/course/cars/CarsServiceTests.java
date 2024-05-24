package com.course.cars;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import com.course.cars.domain.dto.CarDTO;
import com.course.cars.domain.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        assertNotNull(id);

        createdCarDTO = service.getById(id);
        assertNotNull(createdCarDTO);

        assertEquals("Car Spring Test", createdCarDTO.getName());
        assertEquals("Car Spring Test", createdCarDTO.getName());

        service.delete(id);

        try {
            assertNull(service.getById(id));
            fail("Car not deleted.");
        } catch (ObjectNotFoundException ex) {
            // OK
        }

    }

    @Test
    public void testList() {
        List<CarDTO> carDTOList = service.getCars();

        assertEquals(30, carDTOList.size());
    }

    @Test
    public void testGetById() {
        CarDTO byId = service.getById(11L);

        assertNotNull(byId);

        assertEquals("Ferrari FF", byId.getName());
    }

    @Test
    public void testSizeGetByType() {
        assertEquals(10, service.getAllByType("classic").size());
        assertEquals(10, service.getAllByType("sport").size());
        assertEquals(10, service.getAllByType("lux").size());

        assertEquals(0, service.getAllByType("x").size());
    }

}
