package my.reservetable.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // 아래 리소스에 대해 스프링시큐리티 기능 비활성화
        return web -> web.ignoring()
                //.requestMatchers("/favicon.ico")
                //.requestMatchers("/error")
                .requestMatchers(PathRequest.toH2Console());
    }

*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http.authorizeHttpRequests( auth -> auth   //요청에 대한 인가 설정
                         .requestMatchers("/css/**","/favicon.*","/error","/js/**","/images/**").permitAll() //정적자원 무시보다 허용해주는 편이 보안상 좋음
                         .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                         .requestMatchers(PathRequest.toH2Console()).permitAll()
                         //.requestMatchers("/h2-console/**").permitAll()
                         .requestMatchers("/","/home").permitAll()
                         .requestMatchers("/auth/login/**","/auth/signup/**").permitAll()
                         .requestMatchers("/shop/**").permitAll()
                         //.requestMatchers("/owners/** ").permitAll()
                         .requestMatchers("/shops/all").permitAll()
                         .anyRequest().authenticated()   // 위의 요청들을 제외한 나머지 요청은 인증이 필요
                 )
                //.formLogin(Customizer.withDefaults())
                 .formLogin(form -> form
                         .loginPage("/auth/login/main")
                         .loginProcessingUrl("/auth/login")
                         .usernameParameter("email")
                         .passwordParameter("password")
                         .defaultSuccessUrl("/owner")
                 )
                 //.userDetailsService(userDetailsService)
                .csrf(AbstractHttpConfigurer::disable);  //csrf 비활성화
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails owner = User.withUsername("owner").password("{noop}1111").roles("OWNER").build();
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        manager.createUser(owner);
        manager.createUser(user);
        return manager;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 기본적으로 bcrypt 암호화 알고리즘의 BCryptPasswordEncoder객체를 생성하고 사용한다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

