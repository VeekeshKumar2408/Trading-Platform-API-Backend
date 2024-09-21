package com.tradingPlatform.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {

  private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

  public static String generateToken(Authentication auth){
      Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
      String roles = populateAuthorities(authorities);

      String jwt = Jwts.builder()
                      .setIssuedAt(new Date())
                      .setExpiration(new Date(new Date().getTime() + 86400000))
                      .claim("email", auth.getName())
                      .claim("authorities", roles)
                      .signWith(key)
                      .compact();
      return jwt;
  }

  public static String getEmailFromToken(String token){
      token = token.substring(7);

      // accessing email
      Claims claims = Jwts.parser()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      return String.valueOf(claims.get("email"));
  }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
      Set<String> auth = new HashSet<>();

      for (GrantedAuthority grantedAuthority : authorities){
           auth.add(grantedAuthority.getAuthority());
      }

      return String.join(",", auth);
    }

}
