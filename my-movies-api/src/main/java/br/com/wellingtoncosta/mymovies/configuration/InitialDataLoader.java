package br.com.wellingtoncosta.mymovies.configuration;

import br.com.wellingtoncosta.mymovies.domain.Role;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.repository.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;

import static java.util.Objects.isNull;

/**
 * @author Wellington Costa on 07/05/17.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Inject
    private UserRepository userRepository;

    @Lazy
    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (alreadySetup) {
            return;
        }

        createAdminUserIfNotFound();

        alreadySetup = true;
    }

    private void createAdminUserIfNotFound() {
        User user = userRepository.findByEmail("admin@email.com");

        if (isNull(user)) {
            Role adminRole = userRepository.findRoleByName("ROLE_ADMIN");

            user = new User();

            user.setName("Administrador");
            user.setEmail("admin@email.com");
            user.setPassword(passwordEncoder.encode("P@ssword"));
            user.setRoles(Collections.singletonList(adminRole));

            userRepository.saveNewUser(user);
        }
    }

}
