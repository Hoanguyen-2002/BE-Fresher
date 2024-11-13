package com.lg.fresher.lgcommerce.repository.account;

import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import com.lg.fresher.lgcommerce.entity.role.Role;
import com.lg.fresher.lgcommerce.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {
    Role findRoleByName(String name);
}
