package com.app.storemanager.user.baseuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<User, Long> {
    Optional<T> findByEmail(String email);


}
