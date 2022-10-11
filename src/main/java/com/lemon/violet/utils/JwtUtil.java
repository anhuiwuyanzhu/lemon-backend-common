package com.lemon.violet.utils;

import io.jsonwebtoken.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    public static String createJwt(String id,String subject,Integer expireTime){
        Calendar cal  = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.SECOND,expireTime);
        Date expireDate = cal.getTime();
        JwtBuilder jwt = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("zhaolonghui")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "zhaolonghui")
                .setExpiration(expireDate);
        return jwt.compact();
    }

    public static Claims parseJwt(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey("zhaolonghui")
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

//    public static void main(String[] args) {
//        String id = UUID.randomUUID().toString();
//        String subject = "1234";
//        int expireTime = 300;
//
//        String jwt = createJwt(id, subject, expireTime);
//        System.out.println(jwt);
//
//        Claims claims = parseJwt(jwt);
//        String subject1 = claims.getSubject();
//        System.out.println(subject1);
//    }
}
