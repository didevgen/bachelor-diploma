package ua.nure.providence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.nure.providence.utils.auth.BaseAuthenticationProvider;
import ua.nure.providence.utils.auth.SuccessHandler;
import ua.nure.providence.filters.AuthFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Autowired
    private BaseAuthenticationProvider authProv;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/v1/login")
                .and().ignoring().antMatchers(HttpMethod.POST, "/api/v1/logout")
                .and().ignoring().antMatchers(HttpMethod.GET, "/api/v1/swagger-ui.html")
                .and().ignoring().antMatchers(HttpMethod.GET, "/api/v1/swagger-resources/**")
                .and().ignoring().antMatchers(HttpMethod.GET, "/api/v1/v2/**")
                .and().ignoring().antMatchers(HttpMethod.GET, "/api/v1/webjars/**")
                .and().ignoring().antMatchers(HttpMethod.GET, "/api/v1/doors/windows/service");
}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProv);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/logout").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .securityContext()
                .and()
                .rememberMe().disable()
                .requestCache().disable()
                .x509().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
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
