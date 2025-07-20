package alliwannadev.shop.common.config;

import alliwannadev.shop.api.product.controller.ProductApiPaths;
import alliwannadev.shop.common.security.JwtAccessDeniedHandler;
import alliwannadev.shop.common.security.JwtAuthenticationEntryPoint;
import alliwannadev.shop.common.security.JwtAuthenticationFilter;
import alliwannadev.shop.core.application.modules.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public RequestMatcher permitAllEndPointMatcher() {
        String[] permitAllUriPatterns = {
                "/", "/favicon.ico", "/error",
                "/v1/auth/sign-in", "/v1/auth/sign-up"
        };

        List<AntPathRequestMatcher> antPathRequestMatchers =
                new ArrayList<>(
                        Arrays.stream(permitAllUriPatterns)
                                .map(AntPathRequestMatcher::new)
                                .toList()
                );
        antPathRequestMatchers.addAll(List.of(
                new AntPathRequestMatcher(ProductApiPaths.V1_PRODUCTS, HttpMethod.GET.name()),
                new AntPathRequestMatcher(ProductApiPaths.V1_PRODUCTS_INFINITE_SCROLL, HttpMethod.GET.name()),
                new AntPathRequestMatcher(ProductApiPaths.V1_PRODUCTS_BY_PRODUCT_ID, HttpMethod.GET.name())
        ));

        return new OrRequestMatcher(
                antPathRequestMatchers.toArray(AntPathRequestMatcher[]::new)
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(permitAllEndPointMatcher()).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
