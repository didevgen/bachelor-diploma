package ua.malibu.ostpc.utils.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ua.malibu.ostpc.models.User;
import ua.malibu.ostpc.services.AuthToken;

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