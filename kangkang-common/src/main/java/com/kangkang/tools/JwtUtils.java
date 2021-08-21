package com.kangkang.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName: JwtUtils
 * @Author: shaochunhai
 * @Date: 2021/8/18 7:37 下午
 * @Description: TODO
 */
public class JwtUtils {

    private static final String SUBJECT = "kangkang";
    //过期时间
    private static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    private static final String APPSECRET_KEY = "kangkang_secret";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String generateJsonWebToken(Map<String,Object> map) {
        String token = Jwts
                .builder()
                .setSubject(SUBJECT)
                .claim("openid", map.get("openid"))
                .claim("session_key",map.get("session_key"))
                .setIssuedAt(new Date())
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return  认证信息，里面包含过期时间，存入的openid等信息
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查token是否过期
     * @param token
     * @return
     */
    public static boolean checkExpiration(String token){

        Claims body = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();

        Date expiration = body.getExpiration();

        return expiration.getTime()<new Date().getTime();
    }
}
