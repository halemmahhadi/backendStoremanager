package com.app.storemanager.user.storemanager.standarduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StandardUserService {
    @Autowired
    StandardUserRepository standardUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public StandardUser save (StandardUser standardUser){
        standardUser.setPassword(passwordEncoder.encode(standardUser.getPassword()));
        return (StandardUser) standardUserRepository.save(standardUser);
    }
    public StandardUser findByEmail(String email) {
        Optional<StandardUser> standardUser = standardUserRepository.findByEmail(email);
        return standardUser.orElse(null);
    }
    public StandardUser getStandardUserRef(String email) {
        return (StandardUser) standardUserRepository.getOne(Long.valueOf(email));
    }


    public StandardUser delete(String email){
        StandardUser standardUser= findByEmail(email);
        standardUserRepository.delete(standardUser);
        return standardUser;
    }

    @Transactional
    public StandardUser editStandardUser(String email,StandardUser standardUser){
        StandardUser StandardUserToEdit=findByEmail(email);
        StandardUserToEdit.setCreationDate(standardUser.getCreationDate());
        StandardUserToEdit.setAddress(standardUser.getAddress());
        StandardUserToEdit.setPhoneNumber(standardUser.getPhoneNumber());
        StandardUserToEdit.setAccountValidTill(standardUser.getAccountValidTill());
        StandardUserToEdit.setFirstName(standardUser.getFirstName());
        StandardUserToEdit.setLastName(standardUser.getLastName());
        StandardUserToEdit.setPhoneNumber(standardUser.getPhoneNumber());
        StandardUserToEdit.setBirthday(standardUser.getBirthday());
        StandardUserToEdit.setPassword(passwordEncoder.encode(standardUser.getPassword()));
        return  StandardUserToEdit;


    }
}
