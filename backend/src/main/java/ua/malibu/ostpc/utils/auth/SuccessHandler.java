package ua.malibu.ostpc.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginToken loginToken = (LoginToken) SecurityContextHolder.getContext().getAuthentication();
        response.setHeader("x-auth-token", loginToken.getAuthToken().getTokenValue());
        response.setStatus(200);
    }
}
