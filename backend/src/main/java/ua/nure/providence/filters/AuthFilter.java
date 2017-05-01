package ua.nure.providence.filters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ua.nure.providence.daos.UserDAO;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.services.AuthToken;
import ua.nure.providence.services.redis.IRedisRepository;
import ua.nure.providence.utils.auth.LoginToken;

import javax.servlet.FilterChain;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean{
    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    @Autowired
    private IRedisRepository<String, String> redisRepository;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) httpServletResponse;
        String curToken = ((HttpServletRequest)httpServletRequest).getHeader("x-auth-token");
        if (curToken == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token not found!");
            return;
        }
        String uuid = redisRepository.get(curToken);
        if (uuid == null) {
            logger.info("Token " + curToken + " has expired");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token not found!");
        } else {
            redisRepository.refreshExpirationTime(curToken);
            AuthToken authToken = new AuthToken(curToken, uuid);
            User user = userDAO.get(uuid);
            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token not found!");
                return;
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(new LoginToken(user.getEmail(), user.getPassword(), authToken, user));
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