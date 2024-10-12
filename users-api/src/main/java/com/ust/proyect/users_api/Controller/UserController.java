package com.ust.proyect.users_api.Controller;

import com.ust.proyect.users_api.Model.Dto.UserDto;
import com.ust.proyect.users_api.Model.Entity.User;
import com.ust.proyect.users_api.Model.MessageResponse;
import com.ust.proyect.users_api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("users")
    public List<User> showAll(){
        return userService.findAll();
//        List<User> getList =  userService.findAll();
//        if(getList==null){
//            return  new ResponseEntity<>(
//                    MessageResponse.builder()
//                            .message("No hay Registros")
//                            .object(null)
//                            .build()
//                    ,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(
//                MessageResponse.builder()
//                        .message("Consulta Exitosa")
//                        .object(getList)
//                        .build()
//                ,HttpStatus.OK
//        );
    }

    @PostMapping("user")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        User userSave =null;
        try {
             userSave = userService.save(userDto);
             userDto = UserDto.builder()
                     .id(userSave.getId())
                     .userName(userSave.getUserName())
                     .rol(userSave.getRol())
                     .phone(userSave.getPhone())
                     .password(userSave.getPassword())
                     .build();

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Save success")
                    .object(userDto)
                    .build(),
                    HttpStatus.CREATED);
        }
        catch (DataAccessException e){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED
            );
        }


    }
    @GetMapping("user/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){

        User user =  userService.findById(id);
        if(user==null){
            return  new ResponseEntity<>(
                    MessageResponse.builder()
                        .message("User dosn't exist")
                        .object(null)
                        .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDto object = UserDto.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .rol(user.getRol())
            .phone(user.getPhone())
            .password(user.getPassword())
            .build();

        return new ResponseEntity<>(
             MessageResponse.builder()
                     .message("Success consult")
                     .object(object)
                     .build()
             ,HttpStatus.OK
            );
    }
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            User userDelete = userService.findById(id);
            userService.delete(userDelete);
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Success deleted")
                            .object(userDelete)
                            .build()
            ,HttpStatus.OK);
        }
        catch (DataAccessException e){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

}
