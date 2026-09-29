package com.example.BookManagement.auth.service;

import com.example.BookManagement.auth.dto.LoginResponse;
import com.example.BookManagement.auth.form.LoginForm;
import com.example.BookManagement.auth.form.RegisterForm;
import com.example.BookManagement.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String keycloakIssuerUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDTO register(RegisterForm registerForm) {
        throw new UnsupportedOperationException("Đăng ký tài khoản trực tiếp qua Keycloak UI hoặc Keycloak Admin API.");
    }

    public LoginResponse proxyLogin(LoginForm loginForm) {
        String tokenEndpoint = keycloakIssuerUri + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("username", loginForm.getLogin());
        body.add("password", loginForm.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            Map<String, Object> responseBody = response.getBody();

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken((String) responseBody.get("access_token"));
            loginResponse.setLogin(loginForm.getLogin());
            loginResponse.setType("Bearer");
            return loginResponse;
        } catch (Exception e) {
            throw new RuntimeException("Xác thực thất bại: Sai thông tin đăng nhập hoặc Keycloak chưa bật Direct Access Grants cho client.", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Spring Security hiện sử dụng OAuth2 Resource Server kiểm tra JWT trực tiếp.");
    }
}