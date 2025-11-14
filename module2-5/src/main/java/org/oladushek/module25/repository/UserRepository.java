package org.oladushek.module25.repository;

import org.oladushek.module25.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findByUsername(String username);
}
