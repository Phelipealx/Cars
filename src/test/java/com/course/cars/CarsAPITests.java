package com.course.cars;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import com.course.cars.domain.dto.CarDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarsAPITests {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private CarsService service;

    private ResponseEntity<CarDTO> getCar(String url) {
        return restTemplate.getForEntity(url, CarDTO.class);
    }

    private ResponseEntity<List<CarDTO>> getCars(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarDTO>>() {
        });
    }

    @Test
    public void testSave() {

        Car car = new Car();
        car.setName("Porshe");
        car.setType("sport");

        ResponseEntity response = restTemplate.postForEntity("/api/v1/cars", car, null);
        System.out.println(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String location = response.getHeaders().get("location").get(0);
        CarDTO carDTO = getCar(location).getBody();

        assertNotNull(carDTO);
        assertEquals("Porshe", carDTO.getName());
        assertEquals("sport", carDTO.getType());

        restTemplate.delete(location);

        assertEquals(HttpStatus.NOT_FOUND, getCar(location).getStatusCode());
    }

    @Test
    public void testList() {
        List<CarDTO> carsDtoList = getCars("/api/v1/cars").getBody();
        assertNotNull(carsDtoList);
        assertEquals(30, carsDtoList.size());
    }

    @Test
    public void testListByType() {

        assertEquals(10, getCars("/api/v1/cars/type/classic").getBody().size());
        assertEquals(10, getCars("/api/v1/cars/type/sport").getBody().size());
        assertEquals(10, getCars("/api/v1/cars/type/lux").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCars("/api/v1/cars/type/xxx").getStatusCode());
    }

    @Test
    public void testGetOk() {

        ResponseEntity<CarDTO> response = getCar("/api/v1/cars/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarDTO carDTO = response.getBody();
        assertEquals("Ferrari FF", carDTO.getName());
    }

    @Test
    public void testGetNotFound() {

        ResponseEntity response = getCar("/api/v1/cars/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
