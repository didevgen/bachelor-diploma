package ua.malibu.ostpc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.malibu.ostpc.filters.*;
import ua.malibu.ostpc.utils.auth.BaseAuthenticationProvider;
import ua.malibu.ostpc.utils.auth.SuccessHandler;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Autowired
    private BaseAuthenticationProvider authProv;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProv);
    }

    //Disable security at all for the sake of debugging
    //REMOVE OVERRIDEN METHOD BEFORE RELEASE
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .successHandler(new SuccessHandler()).permitAll()
        .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable()
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class);
    }

    @Bean
    public FilterRegistrationBean authBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(authFilter);
        bean.setUrlPatterns(Arrays.asList("/**"));
        return bean;
    }

}
