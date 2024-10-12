package com.ust.proyect.users_api.Service;

import com.ust.proyect.users_api.Model.Dto.UserDto;
import com.ust.proyect.users_api.Model.Entity.User;

import java.util.List;

public interface UserService {
    User save(UserDto userDto);
    User findById(Integer id);
    List<User> findAll();
    void delete(User userDto);
}
