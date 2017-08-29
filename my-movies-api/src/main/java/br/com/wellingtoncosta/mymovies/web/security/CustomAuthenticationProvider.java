package br.com.wellingtoncosta.mymovies.web.security;

import br.com.wellingtoncosta.mymovies.domain.Role;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.exception.BadPasswordException;
import br.com.wellingtoncosta.mymovies.exception.EmptyPasswordException;
import br.com.wellingtoncosta.mymovies.exception.EmptyUsernameException;
import br.com.wellingtoncosta.mymovies.exception.UserNotFoundException;
import br.com.wellingtoncosta.mymovies.service.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author  Wellington Costa on 03/05/17.
 */
@Service
@Transactional
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Inject
    private PasswordEncoder encoder;

    @Inject
    private UserService userService;

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(
            String email,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        if (StringUtils.isEmpty(email)) {
            setHideUserNotFoundExceptions(false);
            throw new EmptyUsernameException();
        }

        String password = (String) authentication.getCredentials();

        if (isNull(password) || StringUtils.isEmpty(password)) {
            throw new EmptyPasswordException();
        }

        User user = userService.findByEmail(email);

        if (isNull(user)) {
            throw new UserNotFoundException();
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadPasswordException();
        }

        return user;
    }

}
