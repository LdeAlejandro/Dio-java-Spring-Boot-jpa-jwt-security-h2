package dio.dio_spring_security_jwt_java.controller;

import dio.dio_spring_security_jwt_java.model.User;
import dio.dio_spring_security_jwt_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public void postUser(@RequestBody User user){ // Recebe um usuário no corpo da requisição para criar usuario
        System.out.println("Creating user");
        service.createUser(user);
    }
}

