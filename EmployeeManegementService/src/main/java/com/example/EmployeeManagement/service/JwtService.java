package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.aspect.LogMethod;
import com.example.EmployeeManagement.model.Roles;
import com.example.EmployeeManagement.security.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long jwtExpiration = 86400000;
    @LogMethod
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    @LogMethod
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    @LogMethod
    public String generateToken(Map<String, Object> extraClaims, AuthUser authUser) {
        return buildToken(extraClaims, authUser);
    }
    @LogMethod
    private String buildToken(Map<String, Object> extraClaims, AuthUser authUser) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(authUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @LogMethod
    public boolean isTokenValid(String token, AuthUser authUser) {
        final String username = extractUsername(token);
        return (username.equals(authUser.getUsername())) && !isTokenExpired(token);
    }
    @LogMethod
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    @LogMethod
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    @LogMethod
    public AuthUser GetAuthUser(String Token){

        Claims claims = extractAllClaims(Token);
        Long id = Long.valueOf(claims.get("id", Integer.class));
        String firstName = claims.get("FirstName", String.class);
        String lastName = claims.get("LastName", String.class);
        String subject = claims.getSubject();
        Roles role = Roles.valueOf(claims.get("role" , String.class));
        boolean isDeleted = claims.get("isDeleted" , Boolean.class);
        boolean isVerified = claims.get("isVerified" , Boolean.class);

        AuthUser.AuthUserBuilder authUser = AuthUser.builder();
        authUser.id(id);
        authUser.firstName(firstName);
        authUser.lastName(lastName);
        authUser.role(role);
        authUser.email(subject);
        authUser.isDeleted(isDeleted);
        authUser.isVerified(isVerified);
        return authUser.build();
    }
    @LogMethod
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    @LogMethod
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
