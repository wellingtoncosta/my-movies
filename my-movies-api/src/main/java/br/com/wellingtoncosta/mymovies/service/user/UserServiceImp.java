package br.com.wellingtoncosta.mymovies.service.user;

import br.com.wellingtoncosta.mymovies.domain.Role;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.exception.UserAlreadyExistsException;
import br.com.wellingtoncosta.mymovies.repository.user.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
@Transactional
@Service("userService")
public class UserServiceImp implements UserService {

    @Lazy
    @Inject
    private PasswordEncoder encoder;

    @Inject
    private UserRepository repository;


    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User saveNewUser(User user) {
        List<User> users = repository.findAll();

        users.forEach(savedUser -> {
            if (savedUser.equals(user)) {
                throw new UserAlreadyExistsException();
            }
        });

        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = repository.findRoleByName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        return repository.saveNewUser(user);
    }

    @Override
    public boolean isAdmin(User user) {
        Role adminRole = repository.findRoleByName("ROLE_ADMIN");
        return user.getRoles()
                .stream()
                .filter(role -> role.getId().equals(adminRole.getId()))
                .count() > 0;
    }
}
