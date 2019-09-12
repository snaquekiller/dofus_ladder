package dofus.service.common;

import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import data.entity.PasswordResetToken;
import data.entity.QUser;
import data.entity.User;
import data.service.PasswordResetTokenPersistenceService;
import data.service.UserPersistenceService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSqlService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Inject
    private UserPersistenceService userPersistenceService;

    @Inject
    private PasswordResetTokenPersistenceService passwordResetTokenPersistenceService;

    /**
     * Create an user if the email is not already exist on the database
     *
     * @param _email
     * @param _name
     * @param _firstname
     * @param _pseudo
     * @return
     */
    public User createUser(final String _email, final String _name, final String _firstname, final String _pseudo,
                           final String _password) {
        Objects.requireNonNull(_email);
        final long count = this.userPersistenceService.count(QUser.user.email.eq(_email));
        if (count == 0L) {
            final User user = new User(_email, _name, _firstname, _pseudo, _password);
            return this.userPersistenceService.save(user);
        }
        return null;
    }

    public Optional<User> findOne(Long _id) {
        Objects.requireNonNull(_id);
        return userPersistenceService.findById(_id);
    }

    public User findByUsername(String _username) throws UsernameNotFoundException {
        log.info("log with credential name: " + _username);
        Objects.requireNonNull(_username);
        return  findByEmail(_username);
    }

    public User findByEmail(String _email) throws UsernameNotFoundException {
        log.info("log with credential email: " + _email);
        Objects.requireNonNull(_email);
        return  userPersistenceService.findOne(QUser.user.email.eq(_email)).orElseGet(null);
    }

    public void createPasswordResetTokenForUser(User _user, String _token) {
        log.info("Create a new password reset token for user : " + _user + "with token " + _token);
        PasswordResetToken myToken = new PasswordResetToken(_token, _user);
        passwordResetTokenPersistenceService.save(myToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("log with credential name: " + username);
        Objects.requireNonNull(username);
        return findByUsername(username);
    }
}
