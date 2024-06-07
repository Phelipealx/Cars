package com.course.cars.api.security.jwt;

import lombok.Data;

@Data
class JwtLoginInput {
    private String username;
    private String password;
}