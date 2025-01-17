package dio.dio_spring_security_jwt_java.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JWTObject {
    private String subject; // Username
    private Date issuedAt; // Data de criação do token
    private Date expiration; // Data de expiração do token
    private List<String> roles; // Lista de permissões do usuário


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setRoles(String... roles) {
        this.roles = Arrays.asList(roles);
    }
}
