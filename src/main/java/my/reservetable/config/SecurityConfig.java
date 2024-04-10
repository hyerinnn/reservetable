package my.reservetable.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.reservetable.error.ErrorResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 기본적으로 bcrypt 암호화 알고리즘의 BCryptPasswordEncoder 객체를 생성하고 사용한다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //TODO : exception handling처리용 클래스 따로 빼기
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            ObjectMapper mapper = new ObjectMapper();
            ErrorResponse responseDto = new ErrorResponse(HttpStatus.FORBIDDEN, "권한없음");
            String responseBody = mapper.writeValueAsString(responseDto);

             response.setContentType("application/json; charset=utf-8");
             response.setStatus(403);
             response.getWriter().println(responseBody);
        });

        http
                .authorizeHttpRequests( auth -> auth   //요청에 대한 인가 설정
                     .requestMatchers("/css/**","/favicon.*","/error","/js/**","/images/**","/swagger-ui/**","/api-docs/**").permitAll() //정적자원 무시보다 허용해주는 편이 보안상 좋음
                     .requestMatchers(PathRequest.toH2Console()).permitAll()
                     .requestMatchers("/","/home").permitAll()
                     .requestMatchers("/auth/login/**","/auth/signup/**").permitAll()
                     //.requestMatchers("/shop/**").permitAll()
                     .requestMatchers("/owners/** ").hasRole("OWNER")
                     .requestMatchers("/shops/all").permitAll()
                     .anyRequest().authenticated()   // 위의 요청들을 제외한 나머지 요청은 인증이 필요
                 )
/*
        // form 로그인으로 진행하는 경우 설정
         .formLogin(form -> form
                 .loginPage("/auth/login/main")
                 .loginProcessingUrl("/auth/login")
                 .usernameParameter("email")
                 .passwordParameter("password")
                 .defaultSuccessUrl("/owner")
         )
*/
                 .formLogin(AbstractHttpConfigurer::disable)
                 // jSessionId를 서버쪽에서 관리하지 않겠다. (jwt와 같이 세션을 사용하지 않는 경우 사용) = 무상태성
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .httpBasic(AbstractHttpConfigurer::disable) // 기본인증 팝업창 사용을 하지 않겠다.
                 .csrf(AbstractHttpConfigurer::disable)  //csrf 비활성화
                 .cors().configurationSource(configurationSource());
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

    // formLogin사용 시, 임시 계정
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails owner = User.withUsername("owner").password("{noop}1111").roles("OWNER").build();
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        manager.createUser(owner);
        manager.createUser(user);
        return manager;
    }

}

