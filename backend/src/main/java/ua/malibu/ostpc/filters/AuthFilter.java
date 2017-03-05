package ua.malibu.ostpc.filters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.services.redis.IRedisRepository;

import javax.servlet.FilterChain;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean{
    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    @Autowired
    private IRedisRepository<String, String> redisRepository;

    @Override
    public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String curToken = ((HttpServletRequest)httpServletRequest).getHeader("x-auth-token");
        if (curToken == null) {
            throw new RestException(HttpStatus.UNAUTHORIZED, 404001, "Token not found!");
        }
        String uuid = redisRepository.get(curToken);
        if (uuid == null) {
            logger.info("Token " + curToken + " has expired");
            throw new RestException(HttpStatus.UNAUTHORIZED, 404001, "Token not found!");
        } else {
            redisRepository.refreshExpirationTime(curToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    public IRedisRepository getIRedisRepository() {
        return redisRepository;
    }

    public void setIRedisRepository(IRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}