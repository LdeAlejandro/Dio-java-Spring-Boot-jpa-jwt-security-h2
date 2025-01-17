package dio.dio_spring_security_jwt_java.security;


import dio.dio_spring_security_jwt_java.config.SecurityDatabaseService;
import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.h2.server.web.WebServlet;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };



        @Bean // Indica que o metodo cria um bean que será gerido pelo Spring
        // Define as autorizacoes para o filtro de autenticacao JWT (UsernamePasswordAuthenticationFilter)
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            System.out.println("****************************************************************");
            System.out.println("WebSecurityConfig SecurityFilterChain");
                http.csrf(customizer -> customizer.disable()); // Desabilita a proteção CSRF (Cross-Site Request Forgery)
              http.cors(customizer -> customizer.disable()); // Desabilita a proteção CORS (Cross-Origin Resource Sharing)
            http.headers(headers -> headers.frameOptions(frameOptions  -> frameOptions.disable()));
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


                http.authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/logoutpls").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
                                .requestMatchers(HttpMethod.GET, "/managers").hasAnyRole("MANAGERS")
                                .anyRequest().authenticated()
                );//.sessionManagement(session -> session
//                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//               );

            //http.formLogin(Customizer.withDefaults()); // habilita de novo a pagina de form login padrao do Spring Security
            http.httpBasic(Customizer.withDefaults()); // habilita de novo a pagina de form login padrao do Spring Security para postman


                return http.build();
        }

//    @Bean //HABILITANDO ACESSAR O H2-DATABSE NA WEB
//    public ServletRegistrationBean h2servletRegistration(){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean((Servlet) new WebServlet());
//        registrationBean.addUrlMappings("/h2-console/*");
//        return registrationBean;
//    }
}