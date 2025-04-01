package com.coderscampus.AssignSubmitApp.services;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts.SIG;

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
        String token = Jwts.builder()
            .claims()
            .issuer("SYSTEM")
            .issuedAt(new Date())
            .subject(userDetails.getUsername())
            .expiration(new Date(this.EXPIRE_IN))
            .and()
            .signWith((Key) SIG.HS512)
            .compact();

        return token;
    }

    @Override
    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClaims'");
    }
}
