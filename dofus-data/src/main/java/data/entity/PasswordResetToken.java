package data.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import data.commons.AbstractDeletableJpaEntity;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;

@Entity
@Table(name = "password_reset", schema = "LN")
@lombok.Data
@ToString(exclude = "password")
public class PasswordResetToken  extends AbstractDeletableJpaEntity<Long> {

    // Minutes expiration
    private static final int EXPIRATION = 60 * 24;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public PasswordResetToken(String _token, User _user) {
        this.token = _token;
        this.user = _user;
        this.expiryDate = (new DateTime()).plusMinutes(EXPIRATION).toDate();
    }
}