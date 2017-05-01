package ua.nure.providence.utils.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ua.nure.providence.models.authentication.User;
import ua.nure.providence.services.AuthToken;

import java.util.Collections;

public class LoginToken extends UsernamePasswordAuthenticationToken {
    private User authenticatedUser;
    private AuthToken authToken;

    public LoginToken(String login, String pass, AuthToken token, User user) {
        super(login, pass, Collections.EMPTY_LIST);
        this.authToken = token;
        this.authenticatedUser = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}