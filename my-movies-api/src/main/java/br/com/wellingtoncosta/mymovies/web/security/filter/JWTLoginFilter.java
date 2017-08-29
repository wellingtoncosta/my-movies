package br.com.wellingtoncosta.mymovies.web.security.filter;

import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.web.security.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

/**
 * @author  Wellington Costa on 03/05/17.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private User user;

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
        Authentication authentication = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        emptyList()
                )
        );

        this.user = (User) authentication.getPrincipal();

        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        res.getWriter().print(new ObjectMapper().writeValueAsString(user));
        TokenAuthenticationService.addAuthentication(res, auth.getName());
    }

}
