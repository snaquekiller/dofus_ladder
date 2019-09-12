package dofus.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import dofus.service.MailService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.entity.PlayerXp;
import data.entity.User;
import data.service.PlayerXpPersistenceService;
import dofus.exception.UserNotFoundException;
import dofus.modele.PlayerGrowthDto;
import dofus.modele.PlayerTemporalData;
import dofus.modele.mapper.PlayerGrowthMapper;
import dofus.modele.mapper.PlayerTemporalDataMapper;
import dofus.modele.request.UserRequestDto;
import dofus.service.common.UserSqlService;
import dofus.service.PasswordService;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@CrossOrigin
@RestController
@Slf4j
public class UserController {


    @Value("${url}")
    private String appUrl;

    @Inject
    private PlayerXpPersistenceService playerXpPersistenceService;

    @PersistenceContext
    private EntityManager mainEntityManager;

    @Inject
    private UserSqlService userSqlService;

    @Inject
    private PasswordService passwordService;

    @Inject
    private MailService mailService;


    /**
     * List reviewers.
     *
     * @return the reviewers response
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@Validated @RequestBody(required = true) final UserRequestDto userRequestDto) {
        final User user = userSqlService
                .createUser(userRequestDto.getEmail(), userRequestDto.getNom(), userRequestDto.getPrenom(),
                        userRequestDto.getPseudo(), passwordService.encode(userRequestDto.getPassword()));
        return user;
    }

    
    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public String resetPassword(HttpServletRequest request, 
    @RequestParam("email") String userEmail) {
        User user = userSqlService.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException(userEmail);
        }
        String token = UUID.randomUUID().toString();
        userSqlService.createPasswordResetTokenForUser(user, token);
        mailService.constructAndSend(appUrl ,request.getLocale(), token, user));
        return "Password Reset";
    }


    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Locale locale, Model model,
            @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = passwordService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message", 
            messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }
}