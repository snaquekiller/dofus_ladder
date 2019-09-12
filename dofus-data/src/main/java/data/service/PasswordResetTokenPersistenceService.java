package data.service;

import data.entity.PasswordResetToken;
import org.springframework.stereotype.Service;

/**
 * .
 */
@Service
public interface PasswordResetTokenPersistenceService extends DataRepository<PasswordResetToken> {

}
