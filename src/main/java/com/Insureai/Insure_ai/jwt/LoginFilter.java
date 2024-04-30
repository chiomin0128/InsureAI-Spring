package com.Insureai.Insure_ai.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


// LoginFilter는 사용자 인증을 처리하기 위한 필터이며, UsernamePasswordAuthenticationFilter를 확장







public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;    // AuthenticationManager 객체를 주입받아 인증 과정을 관리합니다.

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }



    @Override      // 사용자가 로그인을 시도할 때 호출되는 메소드입니다.
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 요청에서 사용자 이름,비밀번호 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // 추출한 사용자 이름과 비밀번호를 사용하여 UsernamePasswordAuthenticationToken 객체를 생성합니다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        System.out.println(username);
        System.out.println(password);

        // 추출한 정보를 기반으로 authenticationManager를 사용하여 사용자를 인증합니다.
        return authenticationManager.authenticate(authToken);
    }


    @Override     // 인증이 성공적으로 완료되면 호출되는 메소드
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("성공입니다.");
    }

    @Override     // 인증이 실패하면 호출되는 메소드입니다.
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("실패 이새끼야");
    }
}
