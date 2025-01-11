package dio.dio_spring_security_jwt_java.repository;

import dio.dio_spring_security_jwt_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Para que o Spring Data JPA entenda que é uma interface de repositório
public interface UserRepository extends JpaRepository<User, Integer> {

    // Para que o Spring Data JPA entenda que é uma consulta personalizada
    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
    public User findByUsername(@Param("username") String username);

    boolean existsByUsername(String username); // Para verificar se o usuário já existe
}