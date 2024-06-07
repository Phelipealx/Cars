package com.course.cars.domain.dto;

import com.course.cars.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserDTO {
    private String username;
    private String name;
    private String email;

    private String tokenJwt;

    public static UserDTO create(User user, String token) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.tokenJwt = token;
        return dto;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}