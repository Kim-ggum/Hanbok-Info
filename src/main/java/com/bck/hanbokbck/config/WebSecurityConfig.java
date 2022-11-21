package com.bck.hanbokbck.config;

import com.bck.hanbokbck.exception.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
//                .cors().disable()
//                .csrf().disable()
//                .headers().frameOptions().disable()
                .csrf().ignoringAntMatchers("/hanbok/notice/upload/image")
                .and()
                .authorizeRequests()
//                  .antMatchers("/noticeboard/**").authenticated() // 인증 요구
//                  .antMatchers("/admin/**").hasRole("ADMIN") // admin만 가능
                    .anyRequest().permitAll() // 인증없이 사용가능
                    .and()
                .formLogin()
                    .loginPage("/member/signinform")
                    .usernameParameter("email")	// 로그인 시 form에서 가져올 id name 값
                    .passwordParameter("pw") // 로그인 시 form에서 가져올 password name 값
                    .loginProcessingUrl("/member/signin") // 로그인을 처리할 URL 입력
                    .defaultSuccessUrl("/hanbok/main")	// 로그인 성공시 url
                    .failureHandler(loginFailureHandler) // 로그인 실패 핸들러
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/member/signout") // 로그아웃을 처리할 URL 입력
                    .invalidateHttpSession(true) // 세션 무효화
                    .logoutSuccessHandler(new LogoutSuccessHandler() { // 로그아웃 성공 시 handler
                        @Override
                        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.sendRedirect("/hanbok/main"); // 이동
                        }
                    })
                    .deleteCookies("JSESSIONID", "remember-me"); // 쿠키 삭제;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
