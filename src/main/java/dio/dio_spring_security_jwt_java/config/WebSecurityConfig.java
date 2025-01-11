package dio.dio_spring_security_jwt_java.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private SecurityDatabaseService securityService;

    @Autowired
    // Utilizado para configurar o gerenciador de autenticacao global
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


        @Bean // Indica que o metodo cria um bean que será gerido pelo Spring
        //defini usuario em memoria
        public UserDetailsService userDetailsService() {

                UserDetails user = User.withUsername("alejandroa")
                        .password("{noop}ab123") // The {noop} prefix is used to specify that the password is stored in plain text and does not require encoding
                        .roles("USERS")
                        .build();

                UserDetails admin = User.withUsername("admin")
                        .password("{noop}master123") // The {noop} prefix is used to specify that the password is stored in plain text and does not require encoding
                        .roles("MANAGERS")
                        .build();

            return new InMemoryUserDetailsManager(user, admin);
        }



        @Bean // Indica que o metodo cria um bean que será gerido pelo Spring
        // Define as autorizacoes para o filtro de autenticacao JWT (UsernamePasswordAuthenticationFilter)
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(customizer -> customizer.disable()); // Desabilita a proteção CSRF (Cross-Site Request Forgery)
              http.cors(customizer -> customizer.disable()); // Desabilita a proteção CORS (Cross-Origin Resource Sharing)

                http.authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/logoutpls").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/managers").hasAnyRole("MANAGERS")
                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
                                .anyRequest().authenticated()
                );//.sessionManagement(session -> session
                      //  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //);

            //http.formLogin(Customizer.withDefaults()); // habilita de novo a pagina de form login padrao do Spring Security
            http.httpBasic(Customizer.withDefaults()); // habilita de novo a pagina de form login padrao do Spring Security para postman


                return http.build();
        }





}