package my.reservetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

/*

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){

*/
/*
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers("/favicon.ico","/error")
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));  // 해당 경로 허용
            }
        };
*//*

        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error");
                //.requestMatchers(toH2Console());

    }

*/


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests()
                    //.requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/test").permitAll()
                    .anyRequest().authenticated()   // 위의 요청들을 제외한 나머지 요청은 인증이 필요
                .and()
                .formLogin()
/*                .formLogin() // form 방식 로그인 사용
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")*/
                .and()
                .csrf(AbstractHttpConfigurer::disable)  //csrf 비활성화
                .build();
    }

}
