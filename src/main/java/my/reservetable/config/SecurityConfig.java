package my.reservetable.config;

import lombok.RequiredArgsConstructor;
import my.reservetable.config.jwt.JwtAuthorizationFilter;
import my.reservetable.config.jwt.JwtTokenProvider;
import my.reservetable.config.jwt.LoginFilter;
import my.reservetable.exception.CustomAccessDeniedEntryPoint;
import my.reservetable.exception.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        // 기본적으로 bcrypt 암호화 알고리즘의 BCryptPasswordEncoder 객체를 생성하고 사용한다.
        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity>{
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            LoginFilter loginFilter = new LoginFilter(authenticationManager);
            loginFilter.setFilterProcessesUrl("/member/login");
            builder.addFilter(loginFilter);
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
/*
                .authorizeHttpRequests( auth -> auth   //요청에 대한 인가 설정
                     .requestMatchers("/css/**","/favicon.*","/error","/js/**","/images/**","/swagger-ui/**","/api-docs/**").permitAll() //정적자원 무시보다 허용해주는 편이 보안상 좋음
                     .requestMatchers(PathRequest.toH2Console()).permitAll()
                     .requestMatchers("/home").permitAll()
                     .requestMatchers("/member/signup/**").permitAll()
                     //.requestMatchers("/member/login/**","/member/signup/**").permitAll()
                     .requestMatchers("/owners/** ").hasRole("OWNER")
                     .requestMatchers("/waitings/owner/**").hasRole("OWNER")
                     .requestMatchers("/waitings/user/**").hasRole("USER")
                     .requestMatchers("/shops/owner/**").hasRole("OWNER")
                     .requestMatchers("/shops/**").permitAll()
                     .anyRequest().authenticated()   // 위의 요청들을 제외한 나머지 요청은 인증이 필요
*/
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/member/owner").hasRole("OWNER")  // 테스트용
                        .requestMatchers("/member/user").hasRole("USER")    // 테스트용
                        .anyRequest().permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                // jSessionId를 서버쪽에서 관리하지 않겠다. (jwt와 같이 세션을 사용하지 않는 경우 사용) = 무상태성
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable) // 기본인증 팝업창 사용을 하지 않겠다.
                .csrf(AbstractHttpConfigurer::disable)  //csrf 비활성화
                .cors().configurationSource(configurationSource());
        // 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new CustomSecurityFilterManager());
        http.addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // 인증되지 않은 사용자(로그인안한 사용자) 접근시 exception
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        // 인증된 사용자가 권한이 없는 곳에 접근시 exception
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedEntryPoint());
        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");    // GET, POST, PUT, DELETE (자바스크립트 요청허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (ex. 프론트엔드 쪽 IP만 허용)
        configuration.setAllowCredentials(true);    // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

