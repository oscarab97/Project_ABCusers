package com.ust.proyect.users_api.Persistence;

import com.ust.proyect.users_api.Model.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
