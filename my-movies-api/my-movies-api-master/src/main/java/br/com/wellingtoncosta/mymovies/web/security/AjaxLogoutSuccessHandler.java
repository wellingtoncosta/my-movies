package br.com.wellingtoncosta.mymovies.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  Wellington Costa on 03/05/17.
 */

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenAuthenticationService.expireToken(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
