package dio.dio_spring_security_jwt_java.security;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

public class JWTCreator {


    public static final String HEADER_AUTHORIZATION = "Authorization"; // Header Authorization
    public static final String ROLES_AUTHORITIES = "authorities"; //  authorities

    // Creating a JWT Token with a prefix and a key
    public static String create(String prefix,String key, JWTObject jwtObject) { // create token

        System.out.println("****************************************************************");
        System.out.println("JWTCreator String create(String prefix,String key, JWTObject jwtObject)");

        //[] secretKey = Base64EncoderForHS512(key);
        System.out.println("****************************************************************");
        System.out.println("Secret key: ");
        System.out.println(key);

        // create token with prefix, key, authorities and roles with jwtObject n
        String token = Jwts.builder() // create token
                .setSubject(jwtObject.getSubject()) // set subject
                .setIssuedAt(jwtObject.getIssuedAt()) // set issuedAt
                .setExpiration(jwtObject.getExpiration()) // set expiration
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles())) // set authorities
                .signWith(SignatureAlgorithm.HS512, key) // set key
                .compact(); // compact token

        System.out.println("****************************************************************");
        System.out.println("RETURNING TOKEN");
        return prefix + " " + token; // return token
    }

    // This method is used for parsing and validating an existing JWT token
    public static JWTObject create(String token,String prefix,String key) // create token object
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {

                System.out.println("****************************************************************");
                System.out.println("JWTCreator create(String token,String prefix,String key) ");
                //byte[] secretKey = Base64EncoderForHS512(key);
                System.out.println("****************************************************************");
                System.out.println("Secret key: ");
                System.out.println(key);

        JWTObject object = new JWTObject();

        //token = token.replace(prefix, "");
        token = token.replace(prefix + " ", "").trim();

                Claims claims = Jwts.parser() // Parser will handle the 3 parts of JWT: header.payload.signature
                        .setSigningKey(key) // set key to parser
                        .build() // build parser
                        .parseClaimsJws(token) // parse token
                        .getPayload(); // get body of token

        object.setSubject(claims.getSubject()); // set subject of token
        object.setExpiration(claims.getExpiration()); // set expiration of token
        object.setIssuedAt(claims.getIssuedAt()); // set issuedAt of token (creation date)
        object.setRoles((List) claims.get(ROLES_AUTHORITIES)); // set roles of token
        return object; // return token

    }
    private static List<String> checkRoles(List<String> roles) {

        System.out.println("****************************************************************");
        System.out.println("JWTCreator checkRoles");
        return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_",""))).collect(Collectors.toList());
    }


}