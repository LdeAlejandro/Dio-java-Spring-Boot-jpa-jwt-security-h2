package dio.dio_spring_security_jwt_java.service;

import dio.dio_spring_security_jwt_java.model.User;
import dio.dio_spring_security_jwt_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService { // serviço para criar usuarios no banco de dados e criptografar as senhas dos mesmos
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(User user){
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass)); // Criptografa a senha do usuario
        repository.save(user); // Salva o usuario no banco de dados

        System.out.println("************************************************************");
        System.out.println("User saved");
        System.out.println(user);
    }
}
