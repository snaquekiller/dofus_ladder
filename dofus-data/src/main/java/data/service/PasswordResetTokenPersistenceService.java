package data.service;

import data.entity.PasswordResetToken;
import data.entity.User;
import org.springframework.stereotype.Service;

/**
 * .
 */
@Service
public interface PasswordResetTokenPersistenceService extends DataRepository<PasswordResetToken> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
