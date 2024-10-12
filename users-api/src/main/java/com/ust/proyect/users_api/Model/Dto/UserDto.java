package com.ust.proyect.users_api.Model.Dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
public class UserDto implements Serializable {
    private Integer id;

    private String userName;

    private String phone;

    private String rol;

    private String password;
}
