//package dio.dio_spring_security_jwt_java.init;
//
//
//import dio.dio_spring_security_jwt_java.model.User;
//import dio.dio_spring_security_jwt_java.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//// Classe para inicialização do banco de dados ao iniciar o aplicativo e inserir os usuários padrões (admin e user)
//public class StartApplication implements CommandLineRunner { //
//    @Autowired
//    private UserRepository repository;
//
//    @Transactional // Indica que o método deve ser executado em uma transação de banco de dados para garantir a integridade dos dados
//    @Override
//    public void run(String... args) throws Exception {
//        User user = repository.findByUsername("admin");
//        if(user==null){
//            user = new User();
//            user.setName("ADMIN");
//            user.setUsername("admin");
//            user.setPassword("master123");
//            user.getRoles().add("MANAGERS");
//            repository.save(user);
//        }
//        user = repository.findByUsername("user");
//        if(user ==null){
//            user = new User();
//            user.setName("USER");
//            user.setUsername("user");
//            user.setPassword("user123");
//            user.getRoles().add("USERS");
//            repository.save(user);
//        }
//    }
//}
