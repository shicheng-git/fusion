package com.fusion.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.fusion.gateway.client.SecretClient;
import com.fusion.gateway.config.FilterProperties;
import com.fusion.gateway.utils.ResponseResult;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Program: fusion
 * @Date: 2021/5/30 16:18
 * @Author: shicheng
 * @Description:
 */
@Slf4j
@Component
public class AccessFilter implements GlobalFilter, Ordered {

    public static List<String> allowPaths;

    public static String secret;

    @Autowired
    private FilterProperties filterProperties;

    @Autowired
    private SecretClient secretClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();//获取请求
        URI uri = request.getURI();
        allowPaths = filterProperties.getAllowPaths();
        String path = uri.getPath();
        log.info("请求路径为：" + path);
        boolean pass = false;
        for (String allow: allowPaths) {
            if (path.startsWith(allow)){
                pass = true;
                break;
            }
        }
        if (!pass){
            //不是白名单，需要校验token
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst("TOKEN");
            if (StringUtils.isBlank(token)){
                ServerHttpResponse response = exchange.getResponse();
                ResponseResult result = ResponseResult.builder()
                        .code(401)
                        .msg("请求不合法")
                        .build();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(dataBuffer));
            }
            if (StringUtils.isBlank(secret)){
                secret = secretClient.getSecret("gateway");
            }
            if (secret == null){
                log.error("获取密文失败，服务不可用");
                ServerHttpResponse response = exchange.getResponse();
                ResponseResult result = ResponseResult.builder()
                        .msg("服务器异常，请稍后再试")
                        .build();
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(dataBuffer));
            }else {
                //密文正常获取，需要校验token
                String msg = null;
                try {
                    SignedJWT jwt = SignedJWT.parse(token);
                    JWSVerifier verifier = new MACVerifier(secret);
                    Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
                    Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");
                    if (!jwt.verify(verifier)) {
                        msg = "Token无效";
                    }else if (new Date().after(expirationTime)) {
                        msg = "Token已过期";
                    }else if (Objects.isNull(account)){
                        msg = "Token校验失败";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = "Token解析失败";
                }
                if (msg == null){//校验通过
                    return chain.filter(exchange);
                }else {//token异常
                    ServerHttpResponse response = exchange.getResponse();
                    ResponseResult result = ResponseResult.builder()
                            .code(401)
                            .msg(msg)
                            .build();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
                    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                    return response.writeWith(Mono.just(dataBuffer));
                }
            }
        }else {
            //白名单，直接通过
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
