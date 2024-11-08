package com.ust.proyect.users_api.Controller;

import com.ust.proyect.users_api.Model.Dto.UserDto;
import com.ust.proyect.users_api.Model.Entity.User;
import com.ust.proyect.users_api.Model.MessageResponse;
import com.ust.proyect.users_api.Service.ExcelExporter;
import com.ust.proyect.users_api.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        System.out.println("invocado crear");
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
        catch (DataAccessException e) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(e.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
    @GetMapping("user/export")
    public ResponseEntity<InputStreamSource> ExportToExcel() throws IOException {

        List<User> listUsers = userService.findAll();

        ExcelExporter excelExporter = new ExcelExporter(listUsers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=query_results.xlsx");

        // Return the Excel file as a byte array in the response body
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(excelExporter.export()));
        //excelExporter.export(response);
    }
    @GetMapping("user/uploadFiles")
    public byte[] uploadFiles() throws IOException{
        Path pathImage = Paths.get("./files/70.JPEG");
        Path pathJson = Paths.get("./files/dishes.json");

        byte[] image= Files.readAllBytes(pathImage);
        byte[] json= Files.readAllBytes(pathJson);

        String jsonData ="";

        ByteArrayOutputStream file ;

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        String header1 = String.format("form-data; name=%s; filename=%s", "attach", "file.pdf");
        String header2 = String.format("form-data; name=%s; filename=%s", "jsonfile", "jsonfile.json");

        builder.part("attach", new ByteArrayResource(image)).header("Content-Disposition", header1);
        builder.part("jsonfile", json).header("Content-Disposition", header2);

        String url = "https://geni-bmw-sc.ust-compassgroup.com/identify-and-match-dishes-marco/?x_api_key=ZBFezMmBuh2pkEXttdz6SwOeMgGGsG2b";
        WebClient webClient = WebClient.create(url);

        byte[] fileContent = null;
        try {
            fileContent = webClient.post()
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();
            return fileContent;
        } catch (Exception e) {
            return null;
        }
    }

}
