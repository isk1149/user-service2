package com.example.userservice2.security;

import com.example.userservice2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//HttpSecurity 부분은 권한에 대한 기능
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().antMatchers("/health_check/**").permitAll();
        // /users/** 경로는 인증 안 해도 됨
        //http.authorizeRequests().antMatchers("/users/**").permitAll();
        //모든 접근 대상에 대하여
        http.authorizeRequests().antMatchers("/**")
                //해당 ip에 대해서만 filter 처리를 위해 넘김
                .hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthenticationFilter());
                //AuthenticationFilter extends UsernamePasswordAuthenticationFilter 이므로
                // /login 요청에 대해서만 해당 필터가 작동할듯?
        //http.headers().frameOptions().disable(); //h2 console 설정
        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter
                = new AuthenticationFilter(authenticationManager(), userService, env);
        //authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        return builder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder).and().build();
    }

    //AuthenticationManagerBuilder는 인증에 대한 부분
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService) //select pwd from users where email=?
//                .passwordEncoder(bCryptPasswordEncoder); //db_pwd(encrypted) == input_pwd(encrypted)
//    }
}
