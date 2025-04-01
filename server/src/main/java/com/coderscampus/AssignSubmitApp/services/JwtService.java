package com.coderscampus.AssignSubmitApp.services;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
    long getExpireIn();
    String getUsername(String token);
    Date getIssuedDate(String token);
    Date getExpirationDate(String token);
    <T> T getClaims(String token, Function<Claims, T> claimsResolver);
    boolean isTokenExpiration(Date expireDate);
    String generateToken(UserDetails userDetails);
}
