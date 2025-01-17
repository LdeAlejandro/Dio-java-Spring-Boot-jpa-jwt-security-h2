package dio.dio_spring_security_jwt_java.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "security.config") // info em resorces application.properties
public class SecurityConfig {

    public static String PREFIX;
    public static String KEY;
    public static Long EXPIRATION;

    public void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public void setKey(String key) {
        KEY = Base64.getEncoder()
                .encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());;
    }

    public void setExpiration(Long expiration) {
        EXPIRATION = expiration;
    }
}
