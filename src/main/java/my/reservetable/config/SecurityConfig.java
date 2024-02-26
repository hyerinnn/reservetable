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

    /**
     * TODO : 리팩토링 필요
     *          1. web.ignorig()으로 인한 warn경고 해결하기 (spring security 5.x부터 경고추가됨) -> permitAll()사용 권장
     *          2. webSecurityCustomizer에서 "/h2-console" matcher추가하면 오류발생 -> 리소스용 SecurityFileChain을 추가하여 수정필요
     *              :임시 : spring버전 3.2.2에서 3.0.5로 버전내리고,  securityFilterChain에 permitAll()로 추가해놓음
     * */

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // 아래 리소스에 대해 스프링시큐리티 기능 비활성화
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error");
                //.requestMatchers(toH2Console());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests()
                    //.requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/home").permitAll()
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
