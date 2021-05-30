package com.shicheng.fusion.auth.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.shicheng.fusion.auth.client.UserClient;
import com.shicheng.fusion.auth.service.AuthService;
import com.shicheng.fusion.auth.utils.SecretProperties;
import com.shicheng.fusion.user.dto.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @Program: fusion
 * @Date: 2021/5/30 9:39
 * @Author: shicheng
 * @Description:
 */
@Slf4j
@Service("authService")
public class AuthServiceImpl implements AuthService {

    public static String TOKEN_SECRET;
    public static String TOKEN_EXPIRE;

    @Autowired
    private SecretProperties secretProperties;

    @Autowired
    private UserClient userClient;

    @Override
    public ResponseUser authentication(String account, String password) {
        ResponseUser responseUser = null;
        try {
            responseUser = userClient.query(account, password);
            if (responseUser != null) {
                String token = buildToken(account);
                responseUser.setToken(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseUser;
    }

    @Override
    public String verifyToken(String token) {
        String refresh = null;
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            if (StringUtils.isBlank(TOKEN_SECRET)){
                TOKEN_SECRET = secretProperties.getSecret();
            }
            JWSVerifier verifier = new MACVerifier(TOKEN_SECRET);
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");

            if (!jwt.verify(verifier)) {
                log.debug("Token 无效");
            }else if (new Date().after(expirationTime)) {
                log.debug("Token 已过期");
            }else if (Objects.isNull(account)){
                log.debug("账号为空");
            }else {
                //刷新token
                refresh = buildToken(String.valueOf(account));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refresh;
    }

    /**
     * 创建token
     * @param account
     * @return
     */
    public String buildToken(String account){
        String token = null;
        try {
            if (StringUtils.isBlank(TOKEN_SECRET)){
                TOKEN_SECRET = secretProperties.getSecret();
            }
            if (StringUtils.isBlank(TOKEN_EXPIRE)){
                TOKEN_EXPIRE = secretProperties.getExpire();
            }
            /**
             * 1.创建一个32-byte的密匙
             */
            MACSigner macSigner = new MACSigner(TOKEN_SECRET.getBytes());
            /**
             * 2. 建立payload 载体
             */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("authentication")
                    .issuer("fusion")
                    .expirationTime(new Date(System.currentTimeMillis() + Long.valueOf(TOKEN_EXPIRE)*1000))
                    .claim("ACCOUNT",account)
                    .build();
            /**
             * 3. 建立签名
             */
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            /**
             * 4. 生成token
             */
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

}
