package dio.dio_spring_security_jwt_java.controller;

import dio.dio_spring_security_jwt_java.dtos.Login;
import dio.dio_spring_security_jwt_java.dtos.Sessao;
import dio.dio_spring_security_jwt_java.model.User;
import dio.dio_spring_security_jwt_java.repository.UserRepository;
import dio.dio_spring_security_jwt_java.security.JWTCreator;
import dio.dio_spring_security_jwt_java.security.JWTObject;
import dio.dio_spring_security_jwt_java.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;


@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
        User user = repository.findByUsername(login.getUsername());
        System.out.println("***************************************************************************************************" );
        System.out.println(user);


        if(user!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }

            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject(); // criando um objeto JWTObject para armazenar as informações do token
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis())); // criando uma data de criação do token
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION))); // criando uma data de expiração do token
            jwtObject.setRoles(user.getRoles()); // criando uma lista de permissoes do token com as permissoes do usuario
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject)); // criando o token
            System.out.println("**********************************************************************");
            System.out.println("Usuario logado com sucesso");
            return sessao;

        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }
}
