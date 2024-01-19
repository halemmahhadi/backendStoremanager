package com.app.storemanager.user.superadmin;

import com.app.storemanager.user.baseuser.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends UserRepository<SuperAdmin> {
}
