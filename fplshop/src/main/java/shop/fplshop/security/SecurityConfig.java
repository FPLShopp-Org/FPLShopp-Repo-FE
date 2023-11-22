package shop.fplshop.security;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import shop.fplshop.entity.Account;
import shop.fplshop.service.AccountService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @Lazy
    @Autowired
    BCryptPasswordEncoder pe;

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Tạo một nguồn dữ liệu đăng nhập tùy chỉnh ở đây
        return username -> {
            try {
                Account user = accountService.findByUsername(username);
                String password = passwordEncoder.encode(user.getPassword());
    
                // Lấy danh sách các vai trò của người dùng
                String role = user.getRole().getName();
    
                return User.withUsername(username).password(password)
                            .roles(role) .build();
            } catch (NoSuchElementException e) {
                throw new UsernameNotFoundException(username + " not found");
            }
        };
    }

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers("/order/**").authenticated()
                .antMatchers("/admin/**").hasAnyRole("STAFF", "ADMIN")
                .antMatchers("/admin/staff-management").hasRole("ADMIN")
                .anyRequest().permitAll());
        http.formLogin(login -> login
                .loginPage("/security/login/form")
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/security/login/success", false)
                .failureUrl("/security/login/error"));
        http.rememberMe(rememberMe -> rememberMe
                .tokenValiditySeconds(86400));
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/unauthorized"));
        http.logout(logout -> logout
                .logoutUrl("/security/logoff")
                .logoutSuccessUrl("/security/logoff/success"));
        return http.build();
    }

    // Cơ chế mã hóa mật khẩu
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
