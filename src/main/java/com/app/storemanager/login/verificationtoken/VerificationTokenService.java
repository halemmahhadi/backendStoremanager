package com.app.storemanager.login.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    public void save(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    public void delete(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public VerificationToken getRefOnVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        return verificationTokenRepository.getOne(verificationToken.getId());
    }

    public void deleteOldVerificationsToken (){
        verificationTokenRepository.deleteAllExpiredSince(java.util.Calendar.getInstance().getTime());
    }
}
