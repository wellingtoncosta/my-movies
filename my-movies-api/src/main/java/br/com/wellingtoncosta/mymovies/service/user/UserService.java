package br.com.wellingtoncosta.mymovies.service.user;

import br.com.wellingtoncosta.mymovies.domain.User;

/**
 * @author Wellington Costa on 29/04/17.
 */
public interface UserService {

    User findByEmail(String email);

    User saveNewUser(User user);

    boolean isAdmin(User user);

}
