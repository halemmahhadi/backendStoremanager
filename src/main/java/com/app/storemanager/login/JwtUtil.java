package com.app.storemanager.login;

import com.app.storemanager.login.verificationtoken.VerificationToken;
import com.app.storemanager.login.verificationtoken.VerificationTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.app.storemanager.common.Common.TOKEN_EXPIRATION_TIME;

@Service
public class JwtUtil {

    @Autowired
    VerificationTokenService verificationTokenService;
    @Value("${storemanager.app.jwtSecret}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractIpAddress(String token) {
        final Claims claims = extractAllClaims(token);
        return (String) claims.get("ipAddress");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, String ipAddress) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("ipAddress", ipAddress);
        String token = createToken(claims, userDetails.getUsername());
        VerificationToken verificationToken = new VerificationToken(token, true);
        verificationTokenService.save(verificationToken);
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                //20 Minutes to expire
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails, String ipAddress) {
        final String username = extractUsername(token);
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && extractIpAddress(token).equals(ipAddress) && verificationToken.isLogIn());
    }

    public void logout(String token) {
        VerificationToken verificationToken = verificationTokenService.getRefOnVerificationToken(token);
        verificationToken.setLogIn(false);
        verificationTokenService.save(verificationToken);
    }
}