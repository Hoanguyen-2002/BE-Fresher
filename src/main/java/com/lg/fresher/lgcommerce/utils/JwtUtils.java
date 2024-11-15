package com.lg.fresher.lgcommerce.utils;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.constant.RedisConstant;
import com.lg.fresher.lgcommerce.constant.TokenConstant;
import com.lg.fresher.lgcommerce.service.redis.RedisService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * -------------------------------------------------------------------------
 * LG CNS Ecommerce
 * ------------------------------------------------------------------------
 *
 * @ Class Name : JwtUtils
 * @ Description : lg_ecommerce_be JwtUtils
 * @ author lg_ecommerce_be Dev Team 63200502
 * @ since 11/5/2024
 * ------------------------------------------------------------------------
 * Modification Information
 * ------------------------------------------------------------------------
 * Date of Revision Modifier Revision
 * ---------------  ---------   ------------------------------------------
 * 11/5/2024       63200502      first creation
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final RedisService redisService;
    private SecretKey secretKey;
    @Value("${lg-commerce.app.jwtSecret:LGCNSSECRETKEY}")
    private String jwtSecret;

    @Value("${lg-commerce.app.jwtExpirationMs:86400000}")
    private int jwtExpirationMs;

    @Value("${lg-commerce.app.refreshExpiration:86400000}")
    private int refreshExpiration;

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field generateSignInKey
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     */
    @PostConstruct
    public void generateSignInKey() {
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), "HmacSHA256");
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field parseJwt
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param request
     * @return String
     */
    public String parseJwt(Object request) {
        String headerAuth = ((HttpServletRequest) request).getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return headerAuth;
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field generateJwtToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param authentication
     * @return String
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String roleName = userPrincipal.getAuthorities().iterator().next().getAuthority();

        Claims claims =
                Jwts.claims()
                        .add("userId", userPrincipal.getUserId())
                        .add("role", roleName)
                        .add("userName", userPrincipal.getUsername())
                        .add("email", userPrincipal.getEmail())
                        .add("status", userPrincipal.getAccountStatus())
                        .subject(userPrincipal.getUsername())
                        .build();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs * 1000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field generateRefreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @return String
     */
    public String generateRefreshToken() {
        return UUIDUtil.generateId();
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field getPayloadFromJwtToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param token
     * @return Claims
     */
    public Claims getPayloadFromJwtToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field validateJwtToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param authToken
     * @return boolean
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken).getPayload();
            String userId = (String) claims.get("userId");
            String key = generateRedisKey(TokenConstant.ACCESS_TOKEN, userId, authToken);
            return claims != null && claims.containsKey("userId") && redisService.isExisted(key);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field validateRefreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param token
     * @return boolean
     */
    public boolean validateRefreshToken(String token, String userId) {
        String key = generateRedisKey(TokenConstant.REFRESH_TOKEN, userId, token);
        return redisService.isExisted(key);
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field saveRefreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * 11/7/2024           63200502    update algrothim generate key
     * <pre>
     * @param userId
     * @param refreshToken
     */
    public void saveToken(String userId, String refreshToken, String accessToken) {
        try {
            String refreshKey = generateRedisKey(TokenConstant.REFRESH_TOKEN, userId, refreshToken);
            String accessKey = generateRedisKey(TokenConstant.ACCESS_TOKEN, userId, accessToken);
            redisService.setValue(refreshKey, userId, refreshExpiration);
            redisService.setValue(accessKey, userId, jwtExpirationMs);
        } catch (Exception e) {
            log.error("Error: Can't save refreshToken");
        }
    }

    /**
     *
     * @ Description : lg_ecommerce_be JwtUtils Member Field saveAccessToken
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/14/2024           63200502    first creation
     *<pre>
     * @param userId
     * @param accessToken
     */
    public void saveAccessToken(String userId, String accessToken){
        try {
            String accessKey = generateRedisKey(TokenConstant.ACCESS_TOKEN, userId, accessToken);
            redisService.setValue(accessKey, userId, jwtExpirationMs);
        } catch (Exception e) {
            log.error("Error: Can't save refreshToken");
        }
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field getUsernameFromRefreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param token
     * @return String
     */
    public String getUserIdFromRefreshToken(String token) {
        String key = generateRedisKey(TokenConstant.REFRESH_TOKEN, token);
        return redisService.getValue(key).toString();
    }

    public void revokeRefreshToken(String token, String userId) {
        String key = generateRedisKey(TokenConstant.REFRESH_TOKEN, userId, token);
        redisService.deleteValue(key);
    }

    /**
     *
     * @ Description : lg_ecommerce_be JwtUtils Member Field revokeAccessToken
     *<pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/7/2024           63200502    first creation
     *<pre>
     * @param request
     */
    public void revokeAccessToken(HttpServletRequest request) {
        String token = parseJwt(request);
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        String userId = (String) claims.get("userId");

        String key = generateRedisKey(TokenConstant.ACCESS_TOKEN, userId, token);
        redisService.deleteValue(key);
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field revokeAllRefreshToken
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/7/2024           63200502    first creation
     * <pre>
     * @param userId
     */
    public void revokeAllToken(String userId) {
        String refreshKey = generateRedisKey(TokenConstant.REFRESH_TOKEN, userId);
        String accessKey = generateRedisKey(TokenConstant.ACCESS_TOKEN, userId);
        redisService.deleteKeysWithPrefix(refreshKey);
        redisService.deleteKeysWithPrefix(accessKey);
    }

    /**
     * @ Description : lg_ecommerce_be JwtUtils Member Field generateRedisKey
     * <pre>
     * Date of Revision Modifier Revision
     * ---------------  ---------   -----------------------------------------------
     * 11/5/2024           63200502    first creation
     * <pre>
     * @param arg
     * @return String
     */
    public String generateRedisKey(String... arg) {
        return String.join(RedisConstant.SEPERATOR, arg);
    }
}
