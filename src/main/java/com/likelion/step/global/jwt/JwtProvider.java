package com.likelion.step.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

  private final SecretKey key;
  private static final long DEFAULT_EXPIRATION = 1000L * 60 * 60 * 3;      // 3시간
  private static final long AUTO_LOGIN_EXPIRATION = 1000L * 60 * 60 * 24 * 14; // 14일

  public JwtProvider(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String createToken(Long memberId, boolean autoLogin) {
    long expiration = autoLogin ? AUTO_LOGIN_EXPIRATION : DEFAULT_EXPIRATION;
    Date now = new Date();
    return Jwts.builder()
        .subject(String.valueOf(memberId))
        .issuedAt(now)
        .expiration(new Date(now.getTime() + expiration))
        .signWith(key)
        .compact();
  }

  // JwtProvider 클래스 안에 아래 메서드 추가
  public Long getMemberId(String token) {
    String subject = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    return Long.valueOf(subject);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}