package com.iffomko.voiceAssistant.security.jwt;

import com.iffomko.voiceAssistant.db.entities.Role;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.authorizationHeader}")
    private String authorizationHeader;
    @Value("${jwt.validityInMilliseconds}")
    private long validityInMilliseconds;
    @Value("${jwt.issuer}")
    private String issuer;

    @Autowired
    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.put("role", role);

        Date currentMoment = new Date();
        Date endMoment = new Date(currentMoment.getTime() + validityInMilliseconds * 1000);

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(currentMoment)
                .setExpiration(endMoment)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuer(issuer)
                .compact();
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Token is expiration or not valid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                "",
                userDetails.getAuthorities()
        );
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
