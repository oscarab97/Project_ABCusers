package com.ust.proyect.users_api.Service.Impl;

import com.ust.proyect.users_api.Model.Dto.UserDto;
import com.ust.proyect.users_api.Model.Entity.User;
import com.ust.proyect.users_api.Persistence.UserRepository;
import com.ust.proyect.users_api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserImpl implements UserService {

    @Autowired// inyeccion de dependencias para generar constructor
    private UserRepository repository;

    @Override
    public User save(UserDto userDto) {
        User user = User.builder().id(userDto.getId())
                .userName(userDto.getUserName())
                .rol(userDto.getRol())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .build();
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return (List) repository.findAll();
    }


    @Transactional
    @Override
    public void delete(User user) {
         repository.delete(user);
    }
}
