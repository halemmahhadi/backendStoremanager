package com.app.storemanager.user.baseuser;


import com.app.storemanager.user.image.Image;
import com.app.storemanager.user.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository<User> userRepository;

    @Autowired
    ImageService imageService;
    @Autowired
    PasswordEncoder passwordEncoder;



    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(()->
                new UserNotFoundException(email));


    }

    public User loadUserByUsername(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        return user.orElse(null);
    }
    public User addUser(User user ){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User deleteUser(String email){
        User user=findByEmail(email);
        userRepository.delete(user);
        return user;
    }
    @Transactional
    public User editUser(String email,UserDto user){
        User userToEdit=findByEmail(email);
        User userToSave = userRepository.getOne(userToEdit.getId());
        userToSave.setAccountValidTill(user.getAccountValidTill());
        userToSave.setAddress(user.getAddress());
        userToSave.setBirthday(user.getBirthday());
        userToSave.setFirstName(user.getFirstName());
        userToSave.setLastName(user.getLastName());
        userToSave.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(userToSave);
    }
    public User getUserRef(String email) {
        return  userRepository.getOne(Long.valueOf(email));
    }

    @Transactional
    public User addImage(String email, Long imageId){
        User user=findByEmail(email);
        Image image=imageService.getById(imageId);
        user.setImage(image);
        return user;
        }
}
