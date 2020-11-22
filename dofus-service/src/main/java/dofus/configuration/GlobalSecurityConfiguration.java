package dofus.configuration;

import dofus.service.common.UserSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@Configuration
public class GlobalSecurityConfiguration{

    public static final String REQUIRE_ADMIN_ROLE = "hasRole('ROLE_ADMIN')";
    public static final String REQUIRE_USER_ROLE = "hasRole('ROLE_USER')";

    @Resource
    private UserSqlService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
