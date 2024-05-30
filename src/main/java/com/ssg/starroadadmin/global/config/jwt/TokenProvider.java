package com.ssg.starroadadmin.global.config.jwt;
// JWT를 생성하고 파싱하여 유효성을 검사하는 역할
// JwtProperties 클래스에서 설정된 값을 사용하여 토큰을 관리

import com.ssg.starroadadmin.user.entity.Manager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Manager manager, Duration expiredAt) {
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), manager);
    }
    // 사용자 객체와 만료 시간(Duration)을 받아 jwt 토큰 생성
    // 파라미터 (사용자 정보, 토큰 유효 기간)
    // 현재 시간을 구하고 expiredAt으로 전달된 기간을 현재 시간에 더해 토큰 만료 시간 expiry 계산
    // creatToken 메소드 호출 실제 토큰 생성


    // 실제 jwt 토큰 생성 사용자 정보와 만료 시간을 받아 구성, 서명해 반환
    public String createToken(Date expiry, Manager manager) {

        Date now = new Date();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));


        // Manager 객체에서 권한을 추출하여 JWT 클레임에 포함
        List<String> roles = manager.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 헤더 타입 jwt
                .setIssuer(jwtProperties.getIssuer()) // 토큰 발급자
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(expiry) // 토큰 만료 시간
                .setSubject(manager.getUsername()) // 사용자 이메일
                .claim("userId", manager.getId()) // 추가 정보 클레임 id : 유저 아이디
                .claim("roles", roles) // 권한 클레임 추가
                .signWith(key) // 서명 비밀값과 합께 해시값을 HS256 방식으로 암호화
                .compact();
    }

    // 제공된 토큰이 유효한지 검사. 토큰의 서명 검증. 파싱 중 발생할 수 있는 예외 처리
// 예외가 발생하면 false 그렇지 않으면 true
    public boolean validToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            Date expiration = claims.getExpiration();
            System.out.println("Token expiration time: " + expiration);

            return !expiration.before(new Date());
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }


    // 토큰 기반으로 Authentication 인증 객체 생성. 시큐리티에서 사용자의 인증 정보 나타내는 데 사용
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        // 토큰에서 클레임 추출
        // 클레임에서 권한 정보를 추출하여 SimpleGrantedAuthority 객체로 변환
        List<String> roles = claims.get("roles", List.class);
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities),
                token,
                authorities
        );
    }

    // 토큰에서 클레임을 추출하는 보조 메소드
    // 토큰의 파싱 담당 클레임 세트 반환
    private Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
        return Jwts.parserBuilder() // 클레임 조회
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(Manager manager) {
        Duration accessTokenValidity = Duration.ofHours(1);
        return generateToken(manager, accessTokenValidity);
    }

    public String generateRefreshToken(Manager manager) {
        Duration refreshTokenValidity = Duration.ofDays(7);
        return generateToken(manager, refreshTokenValidity);
    }
}
