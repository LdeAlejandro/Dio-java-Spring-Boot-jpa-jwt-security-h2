package dio.dio_spring_security_jwt_java.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class WelcomeController {

    // This method is for printing user roles in the console (useful for debugging)
    public void printUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Print roles
        authorities.forEach(authority -> System.out.println("Role: " + authority.getAuthority()));
    }

    // Endpoint that returns a welcome message with session ID
    @GetMapping("/")
    public String welcome(HttpServletRequest request) {
        // Get the current authentication, authorities, and username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // This gets the username
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return "Welcome to My Spring Boot Web API " + request.getSession().getId()  + username + " and roles: " + authorities.toString();
    }

    // Endpoint that is secured for users with "USERS" or "MANAGERS" roles
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('USERS', 'MANAGERS')")
    public String users(HttpServletRequest request) {
        return "Authorized user " + request.getSession().getId();
    }

    // Endpoint that is secured for users with the "MANAGERS" role
    @GetMapping("/managers")
    @PreAuthorize("hasRole('MANAGERS')")
    public String managers(HttpServletRequest request) {


        // Return a message with the session ID, username, and roles
        return "Authorized manager " + request.getSession().getId() + " with username: ";
    }



}
