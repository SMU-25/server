package final_project.momeasy.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.auth.service.CustomOAuthUserService;
import final_project.momeasy.global.security.filter.CustomLoginFilter;
import final_project.momeasy.global.security.filter.JwtAuthorizationFilter;
import final_project.momeasy.global.security.handler.CustomLogoutHandler;
import final_project.momeasy.global.security.handler.JwtAccessDeniedHandler;
import final_project.momeasy.global.security.handler.JwtAuthenticationEntryPoint;
import final_project.momeasy.global.security.handler.OAuth2LoginSuccessHandler;
import final_project.momeasy.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepository;
    private final TokenService tokenService;

    // AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    // 인가 실패 예외 처리 Handler
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // 인증 실패 예외 처리 Handler
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 로그아웃 Handler
    private final CustomLogoutHandler logoutHandler;

    private final CustomOAuthUserService customOAuthUserService;

    // OAuth2 Handler
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, parentRepository);
    }

    private final String[] allowUrl = {
            "/",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/api/auth/signup",
            "/api/auth/login",
            "/api/email/**",
            "/oauth2/**",
            "/login/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        CustomLoginFilter loginFilter = new CustomLoginFilter(
                authenticationManager(authenticationConfiguration), jwtUtil, tokenService);
        loginFilter.setFilterProcessesUrl("/api/auth/login");

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrl).permitAll()  // allowUrl은 모두 허용
                        .anyRequest().authenticated())          // 이외의 요청은 인증 필요

                // JWT Filter 설정
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)

                // csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // httpBasic 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // formLogin 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                // 인증 인가 예외 처리
                .exceptionHandling(handler -> handler
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                // oauth2 로그인
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuthUserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                )

                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");

                            ObjectMapper objectMapper = new ObjectMapper();

                            CustomResponse<String> customResponse = CustomResponse.onSuccess(HttpStatus.OK, "로그아웃 성공");

                            String json = objectMapper.writeValueAsString(customResponse);
                            response.getWriter().write(json);
                        })
                );

        return http.build();
    }
}
