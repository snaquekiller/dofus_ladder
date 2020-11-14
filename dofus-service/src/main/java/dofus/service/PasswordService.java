package dofus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import data.entity.QPasswordResetToken;
import org.springframework.stereotype.Service;

import data.entity.PasswordResetToken;
import data.service.PasswordResetTokenPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * .
 */
@Slf4j
@Service
public class PasswordService {

    @Inject
    private PasswordResetTokenPersistenceService passwordResetTokenPersistenceService;

    public String encode(String _password){
        String bcrypt =  new BCryptPasswordEncoder().encode(_password);//"test"
        log.info("Crypt a new password" + bcrypt);
        return bcrypt;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    public PasswordResetToken findByToken(String _token) {
        log.info("Find a token");
        return passwordResetTokenPersistenceService.findOne(QPasswordResetToken.passwordResetToken.token.eq(_token))
                .orElseGet(null);
    }

    public String validatePasswordResetToken(String _token) {
        // final Page<Chapter> all = chapterPersistenceService.findAll(QChapter.chapter.manga.id.eq(id), pageable);
        //TODO finish htis
        // https://www.baeldung.com/spring-security-registration-i-forgot-my-password
        final PasswordResetToken passToken = findByToken(_token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }
}
