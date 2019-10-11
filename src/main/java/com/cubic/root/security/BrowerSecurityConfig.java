package com.cubic.root.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/login.html", "/index.html").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login.html")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        String type = request.getParameter("type");
                        System.out.println("type:" + type);
//                       if(null!=type&&type.equalsIgnoreCase("ajax")){
                        response.setHeader("Access-Control-Allow-Origin",
                                "*");
                        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
                        response.setHeader("Access-Control-Max-Age", "3600");
                        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"data\":\"true\"}");
//                       }else {
//                           this.getRedirectStrategy().sendRedirect(request,response,"/");
//                       }
                    }
                })
                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws ServletException, IOException {
                        logger.info("MyAuthenticationSuccessHandler login failure!");
                        String type = request.getParameter("type");
                        if (null != type && type.equalsIgnoreCase("ajax")) {
                            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                            response.setHeader("Access-Control-Allow-Origin",
                                    "*");
                            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
                            response.setHeader("Access-Control-Max-Age", "3600");
                            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                            response.setHeader("Access-Control-Allow-Credentials", "true");
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"data\":\"false\"}");
                        } else {
//            response.sendRedirect("/login.html?error");
                            getRedirectStrategy().sendRedirect(request, response, "/login.html?error");
//            super.onAuthenticationFailure(request, response, exception);
                        }
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
