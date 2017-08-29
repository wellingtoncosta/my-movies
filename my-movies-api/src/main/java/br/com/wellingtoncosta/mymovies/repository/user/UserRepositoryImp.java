package br.com.wellingtoncosta.mymovies.repository.user;

import br.com.wellingtoncosta.mymovies.domain.QRole;
import br.com.wellingtoncosta.mymovies.domain.QUser;
import br.com.wellingtoncosta.mymovies.domain.Role;
import br.com.wellingtoncosta.mymovies.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
@Repository("userRepository")
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JPAQueryFactory queryFactory;


    @Override
    public List<User> findAll() {
        return queryFactory.selectFrom(QUser.user).fetch();
    }

    @Override
    public User findByEmail(String email) {
        QUser queryUser = QUser.user;
        QRole queryRole = QRole.role;

        return queryFactory.selectFrom(queryUser)
                .join(queryUser.roles, queryRole)
                .where(queryUser.email.eq(email))
                .fetchOne();
    }

    @Override
    public User saveNewUser(User user) {
        entityManager.persist(user);
        return user;
    }

    public Role findRoleByName(String name) {
        QRole queryRole = QRole.role;
        return queryFactory.selectFrom(queryRole)
                .where(queryRole.name.equalsIgnoreCase(name))
                .fetchOne();
    }

    public Role saveNewRole(Role role) {
        entityManager.persist(role);
        return role;
    }


}
