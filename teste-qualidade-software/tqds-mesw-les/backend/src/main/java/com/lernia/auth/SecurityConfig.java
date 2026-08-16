package com.lernia.auth;

import com.lernia.auth.oauth2.CustomOAuth2UserService;
import com.lernia.auth.oauth2.OAuth2AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                          OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins:http://localhost:4200}") String corsOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(corsOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        configuration.setAllowedOriginPatterns(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                   SecurityContextRepository securityContextRepository) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource(null)))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler())
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(oauth2AuthenticationSuccessHandler)
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(customAuthenticationEntryPoint())
            )
            .securityContext(context -> context.securityContextRepository(securityContextRepository))
            .authorizeHttpRequests(auth -> auth
                // Rotas Públicas
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/login", "/register", "/logout", "/api/auth/password/forgot", "/api/auth/password/reset").permitAll()
                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                .requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll()
                .requestMatchers("/api/favorites/**", "/api/favorites", "/api/auth/me", "/api/auth/logout", "/api/users/**").permitAll()
                .requestMatchers(HttpMethod.GET,
                    "/api/profile/**",
                    "/api/courses/**",
                    "/api/university/**",
                    "/api/area-of-study",
                    "/api/reviews/**",
                    "/api/scholarship/**"
                ).permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/admin/**").permitAll()

                // Rotas Autenticadas
                .requestMatchers(HttpMethod.DELETE, "/api/profile/delete/**", "/api/subscriptions").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/profile/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/api/profile/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/subscriptions").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/users/me/status", "/api/recommendations").authenticated()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    private LogoutSuccessHandler customLogoutSuccessHandler() {
        return (request, response, authentication) -> 
            writeJsonResponse(response, HttpServletResponse.SC_OK, "{\"message\": \"Logged out successfully\"}");
    }

    private AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            if (request.getRequestURI().startsWith("/api/")) {
                writeJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, 
                    "{\"error\": \"Unauthorized\", \"message\": \"Please log in first\"}");
            } else {
                response.sendRedirect("/oauth2/authorization/google");
            }
        };
    }

    private void writeJsonResponse(HttpServletResponse response, int status, String jsonBody) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonBody);
    }
}