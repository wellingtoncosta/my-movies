package br.com.wellingtoncosta.mymovies.web.controller;

import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.service.user.UserService;
import br.com.wellingtoncosta.mymovies.web.security.TokenAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Wellington Costa on 29/04/17.
 */
@RestController
@RequestMapping("api/users")
public class UserController {

    @Inject
    private UserService userService;


    @RequestMapping(method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveNweUser(@RequestBody User user, HttpServletResponse response) {
        User newUser = userService.saveNewUser(user);
        TokenAuthenticationService.addAuthentication(response, newUser.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
