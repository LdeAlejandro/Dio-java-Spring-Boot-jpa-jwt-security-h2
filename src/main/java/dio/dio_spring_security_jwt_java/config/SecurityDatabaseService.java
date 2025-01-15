package dio.dio_spring_security_jwt_java.config;

import dio.dio_spring_security_jwt_java.model.User;
import dio.dio_spring_security_jwt_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityDatabaseService  implements UserDetailsService {
    @Autowired // Injeção de dependência
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userEntity = userRepository.findByUsername(username); // Busca o usuário
        if (userEntity == null) { // Se não encontrar
            throw new UsernameNotFoundException(username); // Lança uma exceção
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(); // Cria um conjunto de autoridades para o usuário

        // Percorre as autoridades do usuário
        userEntity.getRoles().forEach(role -> {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + role)); // Adiciona as autoridades ao conjunto de autoridades do usuário

        });

        // Cria um usuário com as informações do usuário e as autoridades
        UserDetails user = new org.springframework.security.core.userdetails.User(userEntity.getUsername(), // Nome
                userEntity.getPassword(), // Senha
                authorities); // Autoridades
        return user; // Retorna o usuário
    }
}
