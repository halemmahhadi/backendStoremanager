package com.app.storemanager.user.storemanager;

import com.app.storemanager.user.baseuser.User;
import com.app.storemanager.user.baseuser.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreManagerRepository <T extends StoreManager> extends UserRepository<StoreManager> {

}
