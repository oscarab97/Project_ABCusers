package com.ust.proyect.users_api.Model.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name= "users")
public class User implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_name")
    @NonNull
    private String userName;

    @Column(name="phone")
    @NonNull
    private String phone;

    @Column(name="rol")
    @NonNull
    private String rol;

    @Column(name="password")
    @NonNull
    private String password;
}
