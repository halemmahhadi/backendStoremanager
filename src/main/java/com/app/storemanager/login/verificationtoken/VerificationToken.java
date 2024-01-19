package com.app.storemanager.login.verificationtoken;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

import static com.app.storemanager.common.Common.TOKEN_EXPIRATION_TIME;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Date expiryDate;
    private boolean logIn;

    public VerificationToken(String token, boolean logIn) {
        this.token = token;
        this.logIn = logIn;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, TOKEN_EXPIRATION_TIME);
        return new Date(cal.getTime().getTime());
    }
}