package dofus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import data.entity.PlayerSucces;
import data.entity.PlayerXp;
import data.service.PlayerSuccesPersistenceService;
import data.service.PlayerXpPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * .
 */
@Slf4j
@Service
public class PasswordService {

    @Inject
    private PlayerXpPersistenceService playerXpPersistenceService;

    public String encode(String _password){
        String bcrypt =  new BCryptPasswordEncoder().encode(_password);//"test"
        log.info("Crypt a new password" + bcrypt);
        return bcrypt;
    }

    public boolean validatePasswordResetToken(long _id, String _token){

    }
}
