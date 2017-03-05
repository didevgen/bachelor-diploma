package ua.malibu.ostpc.utils.auth;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.AuthToken;
import ua.malibu.ostpc.services.UserService;
import ua.malibu.ostpc.utils.MD5;

import java.security.NoSuchAlgorithmException;

@Component
public class BaseAuthenticationProvider implements AuthenticationProvider {
    protected static final Logger logger = Logger.getLogger(BaseAuthenticationProvider.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        User user = userService.getUserByEmail((String)authentication.getPrincipal());
        try {
            if (user == null) {
                logger.info("User with uuid " + authentication.getPrincipal() + " was not found.");
                throw new RestException(HttpStatus.NOT_FOUND, 404004, "User " + authentication.getPrincipal() + " not found.");
            } else if (!MD5.encrypt((String) authentication.getCredentials()).equals(user.getPassword())) {
                throw new RestException(HttpStatus.UNAUTHORIZED, 401003, "Bad credentials!");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Encryption algorithm error");
        }
        AuthToken authToken = new AuthToken(tokenGenerator.issueToken(user.getUuid()), user.getUuid());
        return new LoginToken(user.getEmail(), user.getPassword(), authToken, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
