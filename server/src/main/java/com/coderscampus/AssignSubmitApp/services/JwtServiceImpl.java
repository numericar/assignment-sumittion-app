package com.coderscampus.AssignSubmitApp.services;

import java.security.Key;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("jwt.secret")
    private String secret;

    private final long EXPIRE_IN = 1000 * 60 * 60 * 24; // millisecons * secons * minute * hours

    @Override
    public long getExpireIn() {
        return this.EXPIRE_IN;
    }

    @Override
    public String getUsername(String token) {
        return this.getClaims(token, Claims::getSubject);
    }

    @Override
    public Date getIssuedDate(String token) {
        return this.getClaims(token, Claims::getIssuedAt);
    }

    @Override
    public Date getExpirationDate(String token) {
        return this.getClaims(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpiration(Date expireDate) {
       return expireDate.before(new Date());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Key secretKey = Keys.hmacShaKeyFor(this.secret.getBytes());

        return Jwts.builder()
                .claims()
                .issuer("SYSTEM")
                .issuedAt(new Date())
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // หมดอายุใน 1 ชั่วโมง
                .and()
                .signWith(secretKey) // ✅ ไม่ต้องระบุ algorithm เพราะระบบจะทำการเลือก algorithm ให้เอง
                .compact();
    }

    @Override
    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClaims'");
    }

    @Override
    public boolean canTokenRefreshed(String token) {
        Date expirationDate = this.getExpirationDate(token);
        return (!this.isTokenExpiration(expirationDate) && ignoreTokenExpire());
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.getUsername(token);
        Date expirationDate = this.getExpirationDate(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpiration(expirationDate));
    }

    @Override
    public boolean ignoreTokenExpire() {
        return false;
    }
}
