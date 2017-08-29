package br.com.wellingtoncosta.mymovies.repository.user;

import br.com.wellingtoncosta.mymovies.domain.Role;
import br.com.wellingtoncosta.mymovies.domain.User;

import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
public interface UserRepository {

    List<User> findAll();

    User findByEmail(String email);

    User saveNewUser(User user);

    Role findRoleByName(String name);

    Role saveNewRole(Role role);

}
