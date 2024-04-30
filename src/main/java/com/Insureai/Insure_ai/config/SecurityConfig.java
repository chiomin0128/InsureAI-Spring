package com.Insureai.Insure_ai.config;

import com.Insureai.Insure_ai.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 스프링의 설정 클래스를 선언합니다.
@EnableWebSecurity // 스프링 시큐리티를 활성화합니다.
public class SecurityConfig {

    // 스프링 시큐리티 설정에 필요한 authenticationConfiguration 객체를 주입합니다.
    private final AuthenticationConfiguration authenticationConfiguration;

    // 생성자를 통해 AuthenticationConfiguration 의존성을 주입받습니다.
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    // AuthenticationManager 빈을 설정합니다. 이는 인증(로그인)을 관리하는 주요 객체입니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // BCryptPasswordEncoder 빈을 설정합니다. 이는 비밀번호를 해싱하기 위한 메커니즘을 제공합니다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain 빈을 설정합니다. 이는 HTTP 보안을 위한 필터 체인을 정의합니다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 기능을 비활성화합니다. JWT가 사용되기 때문에 필요하지 않습니다.
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 기반 로그인을 비활성화합니다. JWT 사용으로 인해 필요하지 않습니다.
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증을 비활성화합니다. JWT 사용으로 인해 필요하지 않습니다.
        http.httpBasic(AbstractHttpConfigurer::disable);

        // HTTP 보안 규칙을 설정합니다.
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/", "/join").permitAll() // 이 경로들은 인증 없이 접근 가능합니다.
                .requestMatchers("/admin").hasRole("ADMIN") // /admin 경로는 ADMIN 역할을 가진 사용자만 접근 가능합니다.
                .anyRequest().authenticated()); // 그 외 다른 모든 요청은 인증을 필요로 합니다.

        // LoginFilter를 추가합니다. 이 필터는 로그인 시 JWT 토큰을 생성하고 검증합니다.
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        // 세션 정책을 STATELESS로 설정합니다. 서버가 세션을 유지하지 않고, 각 요청마다 토큰을 확인합니다.
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 구성된 HttpSecurity 객체를 반환합니다.
        return http.build();
    }
}
