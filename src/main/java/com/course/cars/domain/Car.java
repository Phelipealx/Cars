package com.course.cars.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

//    @Nullable
//    private String description;
//
//    @Nullable
//    private String urlPhoto;
//
//    @Nullable
//    private String urlVideo;
//
//    @Nullable
//    private String latitude;
//
//    @Nullable
//    private String longitude;
}
