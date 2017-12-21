package com.yestic.winter.component;

import com.alibaba.fastjson.JSONObject;
import com.yestic.winter.dto.JwtUser;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * token 处理
 * Created by chenyi on 2017/12/19
 */
@Component
public class JwtDto {

    public SecretKey generalKey() {
        String stringKey = "7786df7fc3a34e26a61c034d5ec8245d";
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    //生成JWT
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = this.generalKey();
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0L) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    //解析JWT
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = this.generalKey();
        JwtParser jwtParser = Jwts.parser();
        jwtParser.requireExpiration(new Date());
        Claims claims = (Claims)Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    //将用户信息转成json格式
    public String generalSubject(JwtUser user) {
        JSONObject jo = new JSONObject();
        jo.put("userId", user.getUserId());
        jo.put("userName", user.getUserName());
        jo.put("userRole", user.getUserRole());
        jo.put("loginIp", user.getLoginIp());
        return jo.toJSONString();
    }

}
