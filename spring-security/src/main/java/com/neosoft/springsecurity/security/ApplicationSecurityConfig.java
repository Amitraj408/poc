package com.neosoft.springsecurity.security;

import com.neosoft.springsecurity.auth.ApplicationUserService;
import com.neosoft.springsecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.neosoft.springsecurity.jwt.TokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.neosoft.springsecurity.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new TokenVerifier(),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/student/v1/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE,"/management/v1/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/v1/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/v1/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/v1/**").hasAnyRole(ADMIN.name(), TRAINEEADMIN.name())
                .anyRequest()
                .authenticated();
//                .and()
////                .httpBasic();
//                    .formLogin()
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/courses",true)
//                .and()
//                .rememberMe()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET")) //if csrf enabled
//                .clearAuthentication(true)                                                        //then delete this line because
//                .invalidateHttpSession(true)                                                      //logout should be Post request
//                .deleteCookies("JSESSIONID","remember-me")
//                .logoutSuccessUrl("/login");

    }

//    @Override
//    @Bean
//   protected UserDetailsService userDetailsService() {
//        UserDetails userAmit = User.builder().username("amitRaj")
//                .password(passwordEncoder.encode("password"))
////                .roles(STUDENT.name()).build();
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminRavi = User.builder().username("ravi")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails traineeAdminDeepak = User.builder().username("deepak")
//                .password(passwordEncoder.encode("password123"))
////                .roles(TRAINEEADMIN.name())
//                .authorities(TRAINEEADMIN.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                userAmit,adminRavi,traineeAdminDeepak
//
//        );
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
