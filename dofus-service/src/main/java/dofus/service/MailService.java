package dofus.service;

import java.io.File;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import data.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@Slf4j
@Service
public class MailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(String _to, String _subject, String _text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(_to);
        message.setSubject(_subject);
        message.setText(_text);
        emailSender.send(message);
    }

    private SimpleMailMessage constructResetTokenEmail(String _contextPath, Locale _locale, String _token, User _user) {
    String url = _contextPath + "/user/changePassword?id=" +
            _user.getId() + "&token=" + _token;
        String message = "";//TODO messages.getMessage("message.resetPassword", null, locale);
    return constructEmail("Reset Password", message + " \r\n" + url, _user);
}

    public void constructAndSend (String _contextPath, Locale _locale, String _token, User _user) {
        emailSender.send(constructResetTokenEmail(_contextPath , _locale, _token, _user));
    }

    private SimpleMailMessage constructEmail(String _subject, String _body, User _user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(_subject);
        email.setText(_body);
        email.setTo(_user.getEmail());
        return email;
    }

    public void sendMail(final String _emailTo, final String _pathFile, final String _fileName)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(_emailTo);
        helper.setSubject(String.format("Your New Story came : %s", _fileName));
        helper.setText("Here your story");

        FileSystemResource file
                = new FileSystemResource(new File(_pathFile));
        helper.addAttachment(_fileName, file);

        emailSender.send(message);
    }
}

