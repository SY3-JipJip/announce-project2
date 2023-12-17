package sit.int204.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.stereotype.Component;
import sit.int204.backend.entities.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private String secretKey; // Secret Key สำหรับการเข้ารหัส Token


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("created", new Date());
        // เพิ่มข้อมูลอื่น ๆ ที่คุณต้องการใน Token

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private Date generateExpirationDate() {
        // ตั้งเวลาหมดอายุของ Token (ในที่นี้ตั้งเป็น 30 นาที)
        return new Date(System.currentTimeMillis() + 30 * 60 * 1000);
    }
}
