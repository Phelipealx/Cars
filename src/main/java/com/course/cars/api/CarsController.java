package com.course.cars.api;

import com.course.cars.domain.Car;
import com.course.cars.domain.CarsService;
import com.course.cars.domain.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    @Autowired
    private CarsService service;

    @GetMapping
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public ResponseEntity<List<CarDTO>> getAll() {
        return ResponseEntity.ok(service.getCars());
    }

    @GetMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getByType(@PathVariable String type) {
        List<CarDTO> cars = service.getAllByType(type);

        return service.getAllByType(type).isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cars);
    }

    @PostMapping
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    public ResponseEntity create(@RequestBody Car car) {
//        try {
        CarDTO newCar = service.create(car);

        URI location = getUri(newCar.getId());
        return ResponseEntity.created(location).build();
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().build();
//        }
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Car car) {
        try {
            CarDTO updatedCar = service.update(id, car);

            return ResponseEntity.ok(updatedCar);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userInfo")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }
}
